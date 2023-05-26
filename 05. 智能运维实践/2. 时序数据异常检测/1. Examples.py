import pandas as pd
from prophet import Prophet

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
    m.plot_components(forecast)