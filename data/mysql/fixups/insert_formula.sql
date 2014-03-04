select * from dbo.FORMULA_T

insert into FORMULA_T
(FORMULAID,name,formula,ANAHA,ACCEPTED)
values(
13,
'BUYE KMP',
'MVAL=100.0;SHURA= 0.0085;ret=qtty*(price/MVAL);ret=ret+ret*SHURA;ret*=-1.0;'
,0
,'2012-01-01 00:00:00'
)
insert into FORMULA_T
(FORMULAID,name,formula,ANAHA,ACCEPTED)
values(
14,
'SELL KMP',
'MVAL=100.0;MAS=0.25;SHURA= 0.0085;ret=qtty*(price/MVAL);ret=ret+ret*SHURA+((Math.abs(qtty)*(price/MVAL))-(Math.abs(qtty)*(orig/MVAL)))*MAS;ret*=-1.0;'
,0
,'2012-01-01 00:00:00'
)
