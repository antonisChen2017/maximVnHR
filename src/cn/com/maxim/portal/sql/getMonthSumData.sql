SELECT
EMPLOYEENO
,sum( CONVERT(float,[ATTENDANCE]) ) AS monthReportX 
,sum( CONVERT(float,[HOLIDAYI] ) ) AS monthReportL
,sum( CONVERT(float,[NOTWORK] ) ) AS monthReportO 
, sum( CONVERT(float,[HOLIDAYB] ) ) AS monthReportB
, sum( CONVERT(float,[HOLIDAYA] ) ) AS monthReportR
, sum( CONVERT(float,[HOLIDAYC] ) ) AS monthReportN
, sum( CONVERT(float,[HOLIDAYD] ) ) AS monthReportH 
, sum( CONVERT(float,[HOLIDAYF] ) ) AS monthReportT 
, sum( CONVERT(float,[HOLIDAYE] ) ) AS monthReportTS
,'' AS monthReportF
,sum( CONVERT(float,[STOPWORK] ) ) AS monthReportW 
, sum( CONVERT(float,[HOLIDAYH] ) ) AS monthReportP 
   FROM 
   VN_DAY_REPORT AS R
   JOIN VN_UNIT AS U On R.UNIT=U.UNIT
   JOIN VN_DEPARTMENT AS D On  R.DEPARTMENT=D.DEPARTMENT
WHERE DAY LIKE '<DAY>/%'
AND <DEPARTMENT/>
AND <UNIT/>
and DATEPART(weekday,DAY)!=1
GROUP BY  R.[EMPLOYEENO]