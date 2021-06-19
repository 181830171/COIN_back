import jieba
import os

def replace_synonym_words(string1):
    # 1读取同义词表，并生成一个字典。
    combine_dict = {}
    # synonymWords.txt是同义词表，每行是一系列同义词，用空格分割
    #获取父目录
    dir_path=os.path.dirname(os.path.realpath(__file__))
    dict_path = dir_path+'/participle_dict/dict.txt'
    jieba.load_userdict(dict_path)
    synonymWords_path=dir_path+"/participle_dict/synonymWords.txt"
    for line in open(synonymWords_path, "r", encoding='utf-8'):
        seperate_word = line.strip().split(" ")
        num = len(seperate_word)
        for i in range(1, num):
            combine_dict[seperate_word[i]] = seperate_word[0]
        #print(seperate_word)
    #print(combine_dict)

    # 2提升某些词的词频，使其能够被jieba识别出来
    jieba.suggest_freq("哈利波特", tune=True)
    jieba.suggest_freq("母亲", tune=True)
    jieba.suggest_freq("父亲", tune=True)

    # 3将语句切分成单词
    seg_list = jieba.cut(string1, cut_all=False)

    f = "/".join(seg_list).encode("utf-8")
    f = f.decode("utf-8")
    #print(f)
    # 4返回同义词替换后的句子
    final_sentence = " "
    for word in f.split('/'):
        if word in combine_dict:
            word = combine_dict[word]
            final_sentence += word
        else:
            final_sentence += word
    # print final_sentence
    return final_sentence
