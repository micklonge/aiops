import pandas as pd
from prophet import Prophet
from prophet.plot import add_changepoints_to_plot

if __name__ == '__main__':
    df = pd.read_csv('examples/example_wp_log_peyton_manning.csv')
    print(df.head())

    m = Prophet()
    m.fit(df)

    future = m.make_future_dataframe(periods=365)
    print(future.tail())

    forecast = m.predict(future)
    print(forecast[['ds', 'yhat', 'yhat_lower', 'yhat_upper']].tail())

    m.plot(forecast)

    m = Prophet(changepoint_prior_scale=0.5)
    forecast = m.fit(df).predict(future)
    fig = m.plot(forecast)

    m = Prophet(changepoint_prior_scale=0.001)
    forecast = m.fit(df).predict(future)
    fig = m.plot(forecast)

    m = Prophet(changepoints=['2014-01-01'])
    forecast = m.fit(df).predict(future)
    fig = m.plot(forecast)

