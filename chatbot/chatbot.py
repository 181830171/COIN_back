from MachineLearning.analyze_question import analysis_question
from MachineLearning.analyze_question1 import analysis_question1

from KnowledgeGraph.get_answer import get_data
from MachineLearning.replace_synonym import replace_synonym_words
import sys
import re


def get_answer(index, params):
    answers = get_data(index, params)
    if index == 0:
        # 出生信息
        print("*" + params[0] + "的出生信息如下：")
    elif index == 1:
        # 血统
        print("*" + params[0] + "的血统为：")
    elif index == 2:
        # 职业
        print("*" + params[0] + "的职业为：")
    elif index == 3:
        # 死亡日期
        print("*" + params[0] + "的死亡日期为：")
    elif index == 4:
        # 组织
        print("*" + params[0] + "参与了以下组织/学院：")
    elif index == 5:
        print("*该问题的答案如下：")
    elif index == 6:
        # 性别
        print("*" + params[0] + "的性别为：")
    elif index == 7:
        # 婚姻状况
        print("*" + params[0] + "的婚姻状况是：")
    elif index == 8:
        # 亲属关系
        print("*" + params[0] + "的" + params[1] + "有：")
    elif index == 9:
        # 学院/组织成员
        print("*" + params[0] + "有以下成员：")
    elif index == 10:
        # 简介
        print("*为您查找到" + params[0] + "的相关信息如下：")
        get_answer(0, params)
        get_answer(1, params)
        get_answer(2, params)
        get_answer(4, params)
        get_answer(6, params)
        get_answer(7, params)
        get_answer(11, params)
    elif index == 11:
        # 家庭信息
        print("*" + params[0] + "的家庭成员包括：")
    elif index == 12:
        # A 和 B 共同组织
        print("*" + params[0] + "和" + params[1] + "共同参与的组织/学院有：")
    elif index == 13:
        # A 和 B 共同亲戚
        print("*" + params[0] + "和" + params[1] + "的共同亲戚有：")

    if index == 5:
        for ans in answers:
            if ans[0] == 0:
                print(params[0] + "不属于" + params[1])
            else:
                print(params[0] + "属于" + params[1])
    elif index == 4:
        for ans in answers:
            if "·" not in ans[0]:
                print(ans[0])
    else:
        for ans in answers:
            print(ans[0])


if __name__ == "__main__":
    question = replace_synonym_words(str(sys.argv[1]))

    # question = input('请输入你的问题:')
    question = re.sub(r'[.?。？；‘*+\n\b\r]', '', question)
    if int(sys.argv[2]) == 0:
        index, params = analysis_question(question)
    elif int(sys.argv[2]) == 1:
        index, params = analysis_question1(question)
    if index != -1:
        get_answer(index, params)
