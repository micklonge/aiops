import pandas as pd
from prophet import Prophet

if __name__ == '__main__':
    df = pd.read_csv('examples/example_wp_log_R.csv')

    df['cap'] = 8.5

    m = Prophet(growth='logistic')
    m.fit(df)

    future = m.make_future_dataframe(periods=1826)
    future['cap'] = 8.5
    fcst = m.predict(future)
    fig = m.plot(fcst)

    df['y'] = 10 - df['y']
    df['cap'] = 6
    df['floor'] = 1.5
    future['cap'] = 6
    future['floor'] = 1.5
    m = Prophet(growth='logistic')
    m.fit(df)
    fcst = m.predict(future)
    fig = m.plot(fcst)