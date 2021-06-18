from MachineLearning.analyze_question import analysis_question
from KnowledgeGraph.get_answer import get_data
import sys


if __name__ == "__main__":
    print("come in")
    #aq = AnalysisQuestion()
    print("come in 2")
    #ga = Get_answer()

    # question = input('请输入你想查询的信息：')
    question = str(sys.argv[1])
    print(question)
    index, params = analysis_question(question)
    print('index params', index, params)
    answers = get_data(index, params)
    # print('答案:',answers)
    for ans in answers:
        print(ans[0])

