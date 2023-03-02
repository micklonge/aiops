#! /usr/bin/env python
# coding:utf-8

"""
    对ex1data2.txt中的数据进行线性回归，所有样本都用来训练和预测
"""
import numpy as np
import matplotlib.pyplot as plt
from sklearn.linear_model import LinearRegression
from mpl_toolkits.mplot3d import Axes3D  # 不要去掉这个import
from sklearn.metrics import mean_squared_error, r2_score
plt.rcParams['font.sans-serif'] = ['SimHei']  # 用来正常显示中文标签
plt.rcParams['axes.unicode_minus'] = False  # 用来正常显示负号

# 数据格式：城市人口,房间数目,房价

# 读取数据
data = np.loadtxt('data.txt', delimiter=',')
data_X = data[:, 0:2]
data_y = data[:, 2]

# 训练模型
model = LinearRegression()
model.fit(data_X, data_y)

# 利用模型进行预测
y_predict = model.predict(data_X)

# 结果可视化
fig = plt.figure()
ax = fig.gca(projection='3d')
ax.scatter(data_X[:, 0], data_X[:, 1], data_y, color='red')
ax.plot(data_X[:, 0], data_X[:, 1], y_predict, color='blue')
ax.set_xlabel('城市人口')
ax.set_ylabel('房间数目')
ax.set_zlabel('房价')
plt.title('线性回归——城市人口、房间数目与房价的关系')
plt.show()

# 模型参数
print(model.coef_)
print(model.intercept_)
# MSE
print(mean_squared_error(data_y, y_predict))
# R^2
print(r2_score(data_y, y_predict))
