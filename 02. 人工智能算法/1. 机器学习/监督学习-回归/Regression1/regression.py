import numpy as np
import matplotlib.pyplot as plt
from sklearn.linear_model import LinearRegression
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_squared_error, r2_score
plt.rcParams['font.sans-serif'] = ['SimHei']  # 用来正常显示中文标签
plt.rcParams['axes.unicode_minus'] = False  # 用来正常显示负号

# 数据格式：城市人口,食品经销商利润

if __name__=='__main__':
    # 读取数据
    data = np.loadtxt('data.txt', delimiter=',')
    data_X = data[:, 0]
    data_y = data[:, 1]

    # 数据分割
    X_train, X_test, y_train, y_test = train_test_split(data_X, data_y)

    # 训练模型
    model = LinearRegression()
    model.fit(X_train.reshape([-1, 1]), y_train)

    # 利用模型进行预测
    y_predict = model.predict(X_test.reshape([-1, 1]))

    # 结果可视化
    plt.scatter(X_test, y_test, color='red')  # 测试样本
    plt.plot(X_test, y_predict, color='blue', linewidth=3)
    plt.xlabel('城市人口')
    plt.ylabel('食品经销商利润')
    plt.title('线性回归——城市人口与食品经销商利润的关系')
    plt.show()

    # 模型参数
    print(model.coef_)
    print(model.intercept_)
    # MSE
    print(mean_squared_error(y_test, y_predict))
    # R^2
    print(r2_score(y_test, y_predict))

