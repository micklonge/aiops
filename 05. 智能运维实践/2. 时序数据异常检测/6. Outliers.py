import pandas as pd
from prophet import Prophet

if __name__ == '__main__':
    df = pd.read_csv('examples/example_wp_log_R_outliers1.csv')
    m = Prophet()
    m.fit(df)
    future = m.make_future_dataframe(periods=1096)
    forecast = m.predict(future)
    fig = m.plot(forecast)

    df.loc[(df['ds'] > '2010-01-01') & (df['ds'] < '2011-01-01'), 'y'] = None
    model = Prophet().fit(df)
    fig = model.plot(model.predict(future))

    ######################################################

    df = pd.read_csv('examples/example_wp_log_R_outliers2.csv')
    m = Prophet()
    m.fit(df)
    future = m.make_future_dataframe(periods=1096)
    forecast = m.predict(future)
    fig = m.plot(forecast)

    ###################################################

    df.loc[(df['ds'] > '2015-06-01') & (df['ds'] < '2015-06-30'), 'y'] = None
    m = Prophet().fit(df)
    fig = m.plot(m.predict(future))
