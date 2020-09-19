from gs_quant.session import GsSession, Environment
from gs_quant.data import Dataset
from datetime import date

GsSession.use(client_id='<client_id>', client_secret='<client_secret>')                     # authenticate GS Session with credentials

dataset = Dataset('COVID19_SUBDIVISION_DAILY_NYT')                                          # initialize the dataset
frame = dataset.get_data(countryId='US', subdivisionId='US-NY', start = date(2019,1,1))     # pull the US data into a Pandas dataframe

print(frame)
