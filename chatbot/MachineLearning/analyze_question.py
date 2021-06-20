import json
import joblib
import numpy as np
import jieba.posseg as pseg
import jieba
import os
import re

# 获取父目录
dir_path = os.path.dirname(os.path.realpath(__file__))
dict_path = dir_path + '/participle_dict/dict.txt'
fullname_path = dir_path + '/participle_dict/fullname.txt'
jieba.load_userdict(dict_path)
vocab_path = dir_path + '/model/vocabulary.json'
model_path = dir_path + '/model/clf.model'
last_result_path = dir_path + '/model/last_result.json'
question_classification_path = dir_path + '/model/question_classification.json'
finished = True


def load_vocab():
    with open(vocab_path, "r") as f:
        vocab = json.loads(f.read())
    return vocab


def load_question_classification():
    with open(question_classification_path, "r") as f:
        question_classification = json.loads(f.read())
    return question_classification


def load_last_result():
    with open(last_result_path, 'r') as lr:
        last_result_tmp = json.loads(lr.read())
    return last_result_tmp


class Last_Result:
    last_nm = ''
    last_nnr = ''
    last_nf = ''
    last_nl = ''
    last_index = -1
    last_finished = False
    last_question = ''
    last_word = ''

    def __init__(self, last_index=-1, last_nm='', last_nnr='', last_nf='', last_nl='', last_finished=True,
                 last_question='', last_word=''):
        self.last_index = last_index
        self.last_nm = last_nm
        self.last_nnr = last_nnr
        self.last_nf = last_nf
        self.last_nl = last_nl
        self.last_finished = last_finished
        self.last_question = last_question
        self.last_word = last_word

    def transJson(self):
        return json.dumps(self.__dict__, ensure_ascii=False)


vocab = load_vocab()
question_class = load_question_classification()
abstractMap = {}

if not os.path.isfile(last_result_path):
    tmp = Last_Result()
    with open(last_result_path, 'w') as lr_tmp:
        lr_tmp.write(tmp.transJson())
last_result = load_last_result()


def abstract_question(question):
    """
    使用jieba进行分词，将关键词进行词性抽象
    :param question:
    :RETURN:
    """
    global finished
    list_word = pseg.lcut(question)  # 中文分词
    cur_result = Last_Result()
    cur_result.last_question = question
    #print('抽象分词结果', list_word)
    abstractQuery = ''
    nm_count = 0
    i = 0
    while i < len(list_word):
        word = list_word[i].word
        pos = str(list_word[i])
        if '·' in word:
            word = list_word[i - 1].word + word + list_word[i + 1].word
            if nm_count == 0:
                abstractQuery += "nm "
                abstractMap['nm'] = word
                cur_result.last_nm = word
                nm_count += 1
            else:
                abstractQuery += "nnr "
                abstractMap['nnr'] = word
                cur_result.last_nnr = word
                nm_count += 1
            i += 1
        elif 'nl' in pos:
            abstractQuery += "nl "
            abstractMap['nl'] = word
            cur_result.last_nl = word
        elif 'nf' in pos:
            abstractQuery += "nr "
            abstractMap['nr'] = word
            cur_result.last_nf = word
        elif 'nr' in pos:
            if i < len(list_word) - 1 and '·' in list_word[i + 1].word:
                pass
            else:
                with open(fullname_path, 'r', encoding='utf-8') as fullname:
                    lines = fullname.read()
                    pattern = re.compile(r'' + word + '·(.*?)\n')
                    pattern2 = re.compile(r'\n(.*?)·' + word)
                    names1 = pattern.findall(lines.__str__())
                    names2 = pattern2.findall(lines.__str__())
                    names = []
                    names.extend(names1)
                    names.extend(names2)
                    if len(names) == 0:
                        abstractQuery += word + " "
                        pass
                    elif len(names) > 1:
                        print('请输入' + word + '的全称。')
                        finished = False
                        cur_result.last_word = word
                        cur_result.last_finished = False
                    else:
                        if len(names1)==1:
                            name = word + '·' + names[0]
                        else:
                            name = names[0] + '·' + word
                        print(word + ' 被认为是 ' + name)
                        if nm_count == 0:
                            abstractQuery += "nm "
                            abstractMap['nm'] = name
                            cur_result.last_nm = name
                            nm_count += 1
                        else:
                            abstractQuery += "nnr "
                            abstractMap['nnr'] = name
                            cur_result.last_nnr = name
                            nm_count += 1
        elif 'r' in pos:
            if word in ['他', '她', '它']:
                if nm_count == 0:
                    abstractQuery += "nm "
                    abstractMap['nm'] = last_result['last_nm']
                    cur_result.last_nm = last_result['last_nm']
                    nm_count += 1
                else:
                    abstractQuery += "nnr "
                    abstractMap['nnr'] = last_result['last_nnr']
                    cur_result.last_nnr = last_result['last_nnr']
                    nm_count += 1
            elif word in ['他们', '它们', '她们']:
                abstractQuery += "nm 和 nnr "
                abstractMap['nm'] = last_result['last_nm']
                abstractMap['nnr'] = last_result['last_nnr']
                cur_result.last_nm = last_result['last_nm']
                cur_result.last_nnr = last_result['last_nnr']
            else:
                abstractQuery += word
        else:
            if i < len(list_word) - 1 and '·' in list_word[i + 1].word:
                pass
            else:
                abstractQuery += word + ' '
        i += 1
    with open(last_result_path, 'w') as lr:
        lr.write(cur_result.transJson())
    return abstractQuery


def query_classify(sentence):
    """
    获取模板索引
    :param sentence:
    :RETURN:
    """
    tmp = np.zeros(len(vocab))
    list_sentence = sentence.split(' ')
    for word in list_sentence:
        if word in vocab:
            tmp[int(vocab[word])] = 1
    clf = joblib.load(model_path)
    index = clf.predict(np.expand_dims(tmp, 0))[0]
    return int(index), question_class[index]


def query_extention(temp):
    """
    模板中的实体值
    :param temp:
    :RETURN:
    """
    params = []
    for abs_key in abstractMap.keys():
        if abs_key in temp:
            params.append(abstractMap[abs_key])
    return params


def analysis_question(question):

    if not last_result['last_finished']:
        question = last_result['last_question'].replace(last_result['last_word'],question)
    abstr = abstract_question(question)
    if not finished:
        index = -1
        return index, ''
    # print('句子抽象化结果：{}'.format(abstr))
    index, strpatt = query_classify(abstr)
    # print('句子对应的索引{}\t模板：{}'.format(index, strpatt))
    finalpatt = query_extention(strpatt)
    # print(finalpatt)
    return index, finalpatt


if __name__ == "__main__":
    # aq = AnalysisQuestion()
    while True:
        last_result = load_last_result()
        question = input('请输入你想查询的信息：')  # 英雄这部电影讲的什么？
        index, params = analysis_question(question)
        #print('index params', index, params)
        finished = True
