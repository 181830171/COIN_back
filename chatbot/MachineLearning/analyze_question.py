import json
import joblib
import numpy as np
import jieba.posseg as pseg
import jieba
import os
import re

#获取父目录
dir_path=os.path.dirname(os.path.realpath(__file__))
dict_path = dir_path+'/participle_dict/dict.txt'
fullname_path = dir_path + '/participle_dict/fullname.txt'
jieba.load_userdict(dict_path)

def load_vocab():
    with open(vocab_path, "r") as f:
        vocab = json.loads(f.read())
    return vocab


def load_question_classification():
    with open(question_classification_path, "r") as f:
        question_classification = json.loads(f.read())
    return question_classification



vocab_path = dir_path+'/model/vocabulary.json'
model_path = dir_path+'/model/clf.model'
question_classification_path = dir_path+'/model/question_classification.json'
vocab = load_vocab()
question_class = load_question_classification()
abstractMap = {}


def abstract_question(question):
    """
    使用jieba进行分词，将关键词进行词性抽象
    :param question:
    :RETURN:
    """
    list_word = pseg.lcut(question)  # 中文分词
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
                nm_count += 1
            else:
                abstractQuery += "nnr "
                abstractMap['nnr'] = word
                nm_count += 1
            i += 1
        elif 'nl' in pos:
            abstractQuery += "nl "
            abstractMap['nl'] = word
        elif 'nf' in pos:
            abstractQuery += "nr "
            abstractMap['nr'] = word
        elif 'nr' in pos:
            if i<len(list_word)-1 and '·' in list_word[i+1].word:
                pass
            else:
                with open(fullname_path,'r',encoding='utf-8') as fullname:
                    lines = fullname.readlines()
                    pattern = re.compile(r''+word+'·(.*?) ')
                    names = pattern.findall(lines.__str__())
                    if len(names) == 0:
                        pass
                    else:
                        name = word + '·' + names[0]
                        print(word + ' 被认为是 ' + name)
                        if nm_count == 0:
                            abstractQuery += "nm "
                            abstractMap['nm'] = name
                            nm_count += 1
                        else:
                            abstractQuery += "nnr "
                            abstractMap['nnr'] = name
                            nm_count += 1
        else:
            if i < len(list_word) - 1 and '·' in list_word[i + 1].word:
                pass
            else:
                abstractQuery += word + " "
        i += 1
    # for item in list_word:
    #     word = item.word
    #     pos = str(item)
    #     if 'nm' in pos:  # 电影名
    #         abstractQuery += "nm "
    #         self.abstractMap['nm'] = word
    #     elif 'nr' in pos and nr_count == 0:
    #         abstractQuery += 'nnt '
    #         self.abstractMap['nnt'] = word
    #         nr_count += 1
    #     elif 'nr' in pos and nr_count == 1:  # nr再一次出现，改成nnr
    #         abstractQuery += "nnr "
    #         self.abstractMap['nnr'] = word
    #         nr_count += 1
    #     elif 'x' in pos:
    #         abstractQuery += "x "
    #         self.abstractMap['x'] = word
    #     else:
    #         abstractQuery += word + " "
    #print('抽象查询结果 ', abstractQuery)
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
    :param sentence:
    :RETURN:
    """
    params = []
    for abs_key in abstractMap:
        if abs_key in temp:
            params.append(abstractMap[abs_key])
    return params


def analysis_question(question):
    #print('原始句子：{}'.format(question))
    abstr = abstract_question(question)
    #print('句子抽象化结果：{}'.format(abstr))
    index, strpatt = query_classify(abstr)
    #print('句子对应的索引{}\t模板：{}'.format(index, strpatt))
    finalpatt = query_extention(strpatt)
    return index, finalpatt


if __name__ == "__main__":
    # aq = AnalysisQuestion()
    question = input('请输入你想查询的信息：')  # 英雄这部电影讲的什么？
    index, params = analysis_question(question)
    print('index params', index, params)
