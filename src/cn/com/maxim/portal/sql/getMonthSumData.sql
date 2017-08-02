SELECT
EMPLOYEENO
,sum( CONVERT(float,[ATTENDANCE]) ) AS monthReportX --上班
,sum( CONVERT(float,[HOLIDAYI] ) ) AS monthReportL --工傷
,sum( CONVERT(float,[NOTWORK] ) ) AS monthReportO  --曠工
, sum( CONVERT(float,[HOLIDAYB] ) ) AS monthReportB --病假
, sum( CONVERT(float,[HOLIDAYA] ) ) AS monthReportR --事假
, sum( CONVERT(float,[HOLIDAYG] ) ) AS monthReportN --調休
, sum( CONVERT(float,[HOLIDAYD] ) ) AS monthReportH --	婚假
, sum( CONVERT(float,[HOLIDAYF] ) ) AS monthReportT --喪假
, sum( CONVERT(float,[HOLIDAYE] ) ) AS monthReportTS --產假
,'' AS monthReportF
, sum( CONVERT(float,[HOLIDAYO] ) ) AS monthReportOT  --公假
, sum( CONVERT(float,[HOLIDAYS] ) ) AS monthReportS  --停工待料
,sum( CONVERT(float,[STOPWORK] ) ) AS monthReportW  --待工
, sum( CONVERT(float,[HOLIDAYH] ) ) AS monthReportP  --年假
, sum( CONVERT(float,[BELATE] ) ) AS monthReportSL  --遲到
, sum( CONVERT(float,[EARLY] ) ) AS monthReportER  --早退
, sum( CONVERT(float,[OVERTIME] ) ) AS monthReportOV  --加班
   FROM 
   VN_DAY_REPORT AS R
   JOIN VN_UNIT AS U On R.UNIT=U.UNIT
   JOIN VN_DEPARTMENT AS D On  R.DEPARTMENT=D.DEPARTMENT
WHERE DAY LIKE '<DAY>/%'
AND <DEPARTMENT/>
AND <UNIT/>
and DATEPART(weekday,DAY)!=1
GROUP BY  R.[EMPLOYEENO]