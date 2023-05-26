import pandas as pd
from prophet import Prophet
from prophet.plot import add_changepoints_to_plot

if __name__ == '__main__':
    df = pd.read_csv('examples/example_air_passengers.csv')
    m = Prophet()
    m.fit(df)
    future = m.make_future_dataframe(50, freq='MS')
    forecast = m.predict(future)
    fig = m.plot(forecast)

    m = Prophet(seasonality_mode='multiplicative')
    m.fit(df)
    forecast = m.predict(future)
    fig = m.plot(forecast)

    fig = m.plot_components(forecast)