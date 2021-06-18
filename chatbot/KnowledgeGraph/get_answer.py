from py2neo import *


#class Get_answer():
#def __init__(self):
   # self.graph = Graph("http://localhost:7474", auth=("neo4j","root"))
graph = Graph("http://localhost:7474", username="neo4j",password="root")

def get_data(index, params):
    query = ''
    if index == 0:
        query = "MATCH (m)<-[:FROM]-(r:Relation)-[:TO]->(n) WHERE r.name='出生' and m.name='{}' RETURN  n.name;".format(params[0]) # 出生
    elif index == 1:
        # 血统
        query = "MATCH (m)<-[:FROM]-(r:Relation)-[:TO]->(n) WHERE r.name='血统' and m.name='{}' RETURN  n.name;".format(params[0])
    elif index == 2:
        # 职业
        query = "MATCH (m)<-[:FROM]-(r:Relation)-[:TO]->(n) WHERE r.name='职业' and m.name='{}' RETURN  n.name;".format(params[0])
    elif index == 3:
        # 死亡日期
        query = "MATCH (m)<-[:FROM]-(r:Relation)-[:TO]->(n) WHERE r.name='逝世' and m.name='{}' RETURN  n.name;".format(params[0])
    elif index == 4:
        # 组织
        query = "MATCH (m)<-[:FROM]-(r:Relation)-[:TO]->(n) WHERE r.name='从属' and m.name='{}' RETURN  n.name;".format(params[0])
    elif index == 5:
        # 组织判断
        query = "MATCH (m)<-[:FROM]-(r:Relation)-[:TO]->(n) WHERE r.name='从属' and m.name='{}' and n.name = '{}' " \
                "RETURN COUNT(*);".format(params[0],params[1])
    elif index == 6:
        # 性别
        query = "MATCH (m)<-[:FROM]-(r:Relation)-[:TO]->(n) WHERE r.name='性别' and m.name='{}' RETURN  n.name;".format(params[0])

    elif index == 7:
        # 婚姻状况
        query = "MATCH (m)<-[:FROM]-(r:Relation)-[:TO]->(n) WHERE r.name='婚姻状况' and m.name='{}' RETURN  n.name;".format(params[0])

    elif index == 8:
        # 亲属关系
        query = "MATCH (m)<-[:FROM]-(r:Relation)-[:TO]->(n) WHERE r.name='{}' and m.name='{}' RETURN  n.name;".format(params[1],params[0])

    elif index == 9:
        # 学院/组织成员
        query = "MATCH (m)<-[:FROM]-(r:Relation)-[:TO]->(n) WHERE r.name='从属' and n.name='{}' RETURN  m.name;".format(params[0])

    elif index == 10:
        # 简介
        query = "MATCH (m)<-[:FROM]-(r:Relation)-[:TO]->(n) WHERE r.name='婚姻状况' and m.name='{}' RETURN  n.name;".format(params[0])

    elif index == 11:
        # 家庭信息
        query = "MATCH (m)<-[:FROM]-(r:Relation)-[:TO]->(n) WHERE r.des='家庭信息' and m.name='{}' RETURN  n.name;".format(params[0])

    elif index == 12:
        # A 和 B 共同组织
        query = "MATCH (m)<-[:FROM]-(r:Relation)-[:TO]->(n) " \
                "MATCH (p)<-[:FROM]-(q:Relation)-[:TO]->(s)" \
                "WHERE r.name='从属' and q.name = '从属' and m.name='{}' and p.name = '{}'" \
                "and n.name = s.name" \
                "RETURN distinct n.name;".format(params[0],params[1])
    elif index == 13:
        # A 和 B 共同亲戚
        query = "MATCH (m)<-[:FROM]-(r:Relation)-[:TO]->(n) " \
                "MATCH (p)<-[:FROM]-(q:Relation)-[:TO]->(s)" \
                "WHERE r.des='家庭信息' and q.des = '家庭信息' and m.name='{}' and p.name = '{}'" \
                "and n.name = s.name" \
                "RETURN distinct n.name;".format(params[0],params[1])

    result = graph.run(query)
    print(query)
    return result


if __name__ == "__main__":
    #ga = Get_answer()
    answers = get_data(1, ['卧虎藏龙'])
    for answer in answers:
        print(answer[0])
