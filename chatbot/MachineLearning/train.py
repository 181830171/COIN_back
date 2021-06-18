import os
import re
import json
import jieba
import joblib
import numpy as np
from sklearn.naive_bayes import GaussianNB


class GenerQuestionClassification():
    def __init__(self):
        self.question_classification_path = "./model/question_classification.json"
        if not os.path.isfile(self.question_classification_path):
            self.save_vocab()

    def save_vocab(self):
        '''这部分 dict 需要和训练数据顺序对应'''
        dic = {'0': 'nm 出生信息',
               '1': 'nm 血统',
               '2': 'nm 职业',
               '3': 'nm 死亡日期',
               '4': 'nm 组织',
               '5': 'nm 是否属于 nl ',
               '6': 'nm 性别',
               '7': 'nm 婚姻状况',
               '8': 'nm 的 nr',
               '9': 'nl 成员',
               '10': 'nm 简介',
               '11': 'nm 家庭信息',
               '12': 'nm 和 nnr 共同组织',
               '13': 'nm 和 nnr 共同亲戚',
               }
        with open(self.question_classification_path, 'w') as f:
            json.dump(dic, f, ensure_ascii=False)  # 会在目录下生成一个*.json的文件，文件内容是dict数据转成的json数据  ensure_ascii=False
        print("save question classification success...")


class GenerVocab():
    '''生成所有训练数据的vocab文件, 使用模型的时候需要. 变更数据的时候需要重新生成.'''
    def __init__(self):
        jieba.load_userdict('participle_dict/dict.txt')
        self.data_path = "./data/"
        self.save_vocab_path = "model/vocabulary.json"
        if not os.path.isfile(self.save_vocab_path):
            self.save_vocab()

    def cut_word(self, file_path):
        result_list = []
        with open(file_path, "r", encoding='utf-8') as temp_f:
            for sentence in temp_f.readlines():
                sentence = sentence.strip()
                temp = jieba.lcut(sentence)
                result_list += temp
        return result_list

    def get_all_word(self):
        all_word_list = []
        for path in os.listdir(self.data_path):
            file_path = os.path.join(self.data_path, path)
            result_word_list = self.cut_word(file_path)
            all_word_list += result_word_list
        all_word_set = set(all_word_list)
        result_dict = {}
        for index, cont in enumerate(all_word_set):
            result_dict[cont] = index
        return result_dict

    def save_vocab(self):
        dic = self.get_all_word()
        with open(self.save_vocab_path, 'w') as f:
            json.dump(dic, f, ensure_ascii=False)  # 会在目录下生成一个*.json的文件，文件内容是dict数据转成的json数据  ensure_ascii=False
        print("save vocab success...")


class Trainer(GenerVocab):
    def __init__(self):
        super().__init__()
        self.vocab = self.load_vocab()

    def load_vocab(self):
        with open(self.save_vocab_path, "r") as f:
            vocab = json.loads(f.read())
        return vocab

    def load_data(self):
        X = []
        Y = []
        list_file = os.listdir(self.data_path)
        for file_name in list_file:
            file_path = os.path.join(self.data_path, file_name)
            result = re.match('【[0-9]*】', file_name).span()
            print(result)
            start = result[0]
            end = result[1]
            with open(file_path, 'r', encoding='utf-8')as fread:
                for line in fread:
                    tmp = np.zeros(len(self.vocab))
                    Y.append(file_name[start + 1:end - 1])  # label
                    list_sentence = jieba.lcut(line.rstrip())
                    for word in list_sentence:
                        if word in self.vocab:
                            tmp[int(self.vocab[word])] = 1
                    X.append(tmp)
        return X, Y

    def train(self):
        X, Y = self.load_data()
        clf = GaussianNB().fit(X, Y)
        joblib.dump(clf, 'model/clf.model')


if __name__ == "__main__":
    gqc = GenerQuestionClassification()
    t = Trainer()
    t.train()
