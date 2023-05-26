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
    fig = m.plot_components(forecast)

    playoffs = pd.DataFrame({
        'holiday': 'playoff',
        'ds': pd.to_datetime(['2008-01-13', '2009-01-03', '2010-01-16',
                              '2010-01-24', '2010-02-07', '2011-01-08',
                              '2013-01-12', '2014-01-12', '2014-01-19',
                              '2014-02-02', '2015-01-11', '2016-01-17',
                              '2016-01-24', '2016-02-07']),
        'lower_window': 0,
        'upper_window': 1,
    })
    superbowls = pd.DataFrame({
        'holiday': 'superbowl',
        'ds': pd.to_datetime(['2010-02-07', '2014-02-02', '2016-02-07']),
        'lower_window': 0,
        'upper_window': 1,
    })
    holidays = pd.concat((playoffs, superbowls))

    m = Prophet(holidays=holidays)
    forecast = m.fit(df).predict(future)

    print(forecast[(forecast['playoff'] + forecast['superbowl']).abs() > 0][
        ['ds', 'playoff', 'superbowl']][-10:])

    fig = m.plot_components(forecast)

    m = Prophet(holidays=holidays)
    m.add_country_holidays(country_name='US')
    m.fit(df)

    forecast = m.predict(future)
    fig = m.plot_components(forecast)


