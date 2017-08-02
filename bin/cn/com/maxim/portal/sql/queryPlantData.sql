DELETE FROM [hr].[dbo].[VN_YEAR_MONTH_PLANT_ATTENDANCE]
      WHERE YMD='<YMD/>'

INSERT INTO [hr].[dbo].[VN_YEAR_MONTH_PLANT_ATTENDANCE]
           ([YMD]
           ,[DEPARTMENT]
           ,[UNIT]
           ,[ROW]
           ,[C1]
           ,[C2]
           ,[C3]
           ,[C4]
           ,[C5]
           ,[C6]
           ,[C7]
           ,[C8]
           ,[C9]
           ,[C10]
           ,[C11]
           ,[C12]
           ,[C13]
           ,[C14]
           ,[C15]
           ,[C16]
           ,[C17]
           ,[C18]
           ,[C19]
           ,[SORT])
select  
'<YMD/>' AS [YMD],
case when(grouping([DEPARTMENT_ID])=1) then '合计' else [DEPARTMENT_ID] end as [DEPARTMENT],  
case when(grouping([UNIT])=1) then '合计' else [UNIT] end  as [UNIT]
,COUNT (*) as [ROW]
,(
	select COUNT(*) AS  EmpInCount
from HR_EMPLOYEE as HE
LEFT OUTER JOIN VN_DEPARTMENT as d ON HE.DEPARTMENT_ID = d.ID
LEFT OUTER JOIN VN_UNIT as V ON HE.UNIT_ID = V.ID
JOIN PWERP_MS.dbo.RsKQResult A  On A.EmpCode=HE.EmpCode
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
WHERE  A.FDate='<YMD/>'
and (B.LeaveFlag='0' Or (B.LeaveFlag='1' And B.LeaveDate>='<YMD/>'))
AND B.Nation<>'0002' 
AND D.ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
	) AS [C1] --外籍幹部實際人數
	,(
select COUNT(*) AS  EmpInCount
from HR_EMPLOYEE as HE
LEFT OUTER JOIN VN_DEPARTMENT as d ON HE.DEPARTMENT_ID = d.ID
LEFT OUTER JOIN VN_UNIT as V ON HE.UNIT_ID = V.ID
JOIN PWERP_MS.dbo.RsKQResult A  On A.EmpCode=HE.EmpCode
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
WHERE  A.FDate='<YESTERDAY/>'
AND B.Nation='0002' 
and (B.LeaveFlag='0' Or (B.LeaveFlag='1' And B.LeaveDate>='<YESTERDAY/>'))
AND D.ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
	) AS [C2]--越籍初期應出勤人數
		,(
select COUNT(*) AS  EmpInCount
from HR_EMPLOYEE as HE
LEFT OUTER JOIN VN_DEPARTMENT as d ON HE.DEPARTMENT_ID = d.ID
LEFT OUTER JOIN VN_UNIT as V ON HE.UNIT_ID = V.ID
JOIN PWERP_MS.dbo.RsKQResult A  On A.EmpCode=HE.EmpCode
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
WHERE  A.FDate='<YMD/>'
AND B.Nation='0002' 
and B.LeaveFlag='0' 
AND D.ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
) 
AS [C3]--"越籍當日應出勤人數"
	,(
	select COUNT(*) AS  EmpInCount
from HR_EMPLOYEE as HE
LEFT OUTER JOIN VN_DEPARTMENT as d ON HE.DEPARTMENT_ID = d.ID
LEFT OUTER JOIN VN_UNIT as V ON HE.UNIT_ID = V.ID
JOIN PWERP_MS.dbo.RsKQResult A  On A.EmpCode=HE.EmpCode
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
WHERE A.FDate='<YMD/>'
AND B.Nation='0002' 
and B.LeaveFlag='0' 
AND D.ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
) AS [C4]--越籍當日實際出勤人數
,(
	select COUNT(*) AS  EmpInCount
from HR_EMPLOYEE as HE
LEFT OUTER JOIN VN_DEPARTMENT as d ON HE.DEPARTMENT_ID = d.ID
LEFT OUTER JOIN VN_UNIT as V ON HE.UNIT_ID = V.ID
JOIN PWERP_MS.dbo.RsKQResult A  On A.EmpCode=HE.EmpCode
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
WHERE A.FDate='<YMD/>'
and B.LeaveFlag='0'
and A.Turn IN ('A1','A2','A3','A4','TX1','TX2')
--AND (A.WorkFTime<>'00:00' and A.WorkETime<>'00:00')
AND D.ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
) AS [C5] --行政班
,(
	select COUNT(*) AS  EmpInCount
from HR_EMPLOYEE as HE
LEFT OUTER JOIN VN_DEPARTMENT as d ON HE.DEPARTMENT_ID = d.ID
LEFT OUTER JOIN VN_UNIT as V ON HE.UNIT_ID = V.ID
JOIN PWERP_MS.dbo.RsKQResult A  On A.EmpCode=HE.EmpCode
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
WHERE A.FDate='<YMD/>'
and B.LeaveFlag='0'
and A.Turn='C1'
--AND (A.WorkFTime<>'00:00' and A.WorkETime<>'00:00')
AND D.ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
) AS [C6] --早班
,(
	select COUNT(*) AS  EmpInCount
from HR_EMPLOYEE as HE
LEFT OUTER JOIN VN_DEPARTMENT as d ON HE.DEPARTMENT_ID = d.ID
LEFT OUTER JOIN VN_UNIT as V ON HE.UNIT_ID = V.ID
JOIN PWERP_MS.dbo.RsKQResult A  On A.EmpCode=HE.EmpCode
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
WHERE A.FDate='<YMD/>'
and B.LeaveFlag='0'
and A.Turn='C2'
--AND (A.WorkFTime<>'00:00' and A.WorkETime<>'00:00')
AND D.ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
)  AS [C7]--中班
,(
	select COUNT(*) AS  EmpInCount
from HR_EMPLOYEE as HE
LEFT OUTER JOIN VN_DEPARTMENT as d ON HE.DEPARTMENT_ID = d.ID
LEFT OUTER JOIN VN_UNIT as V ON HE.UNIT_ID = V.ID
JOIN PWERP_MS.dbo.RsKQResult A  On A.EmpCode=HE.EmpCode
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
WHERE A.FDate='<YMD/>'
and B.LeaveFlag='0'
and A.Turn='C3'
--AND (A.WorkFTime<>'00:00' and A.WorkETime<>'00:00')
AND D.ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
) AS [C8]--夜班
,(
	select COUNT(*) AS  EmpInCount
from HR_EMPLOYEE as HE
LEFT OUTER JOIN VN_DEPARTMENT as d ON HE.DEPARTMENT_ID = d.ID
LEFT OUTER JOIN VN_UNIT as V ON HE.UNIT_ID = V.ID
JOIN PWERP_MS.dbo.RsKQResult A  On A.EmpCode=HE.EmpCode
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
WHERE A.FDate='<YMD/>'
and (B.LeaveFlag='0' Or (B.LeaveFlag='1' And B.LeaveDate>='<YMD/>'))
and A.Turn IN ('CD','CD1','CN','TS1','TS2','CNN')
--AND (A.WorkFTime<>'00:00' and A.WorkETime<>'00:00')
AND D.ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
) AS [C9]--特加班次
,(
	select COUNT(*) AS  EmpInCount
from HR_EMPLOYEE as HE
LEFT OUTER JOIN VN_DEPARTMENT as d ON HE.DEPARTMENT_ID = d.ID
LEFT OUTER JOIN VN_UNIT as V ON HE.UNIT_ID = V.ID
JOIN PWERP_MS.dbo.RsKQResult A  On A.EmpCode=HE.EmpCode
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 LEFT JOIN hr.dbo.VN_LEAVECARD L On  L.EP_ID=HE.ID
   LEFT JOIN hr.dbo.VN_LHOLIDAY Y On  L.HD_ID=Y.ID
WHERE A.FDate=  '<YMD/>' 
and L.LEAVEAPPLY IN ('1')
and Y.HOLIDAYCLAS IN ('E')
and (B.LeaveFlag='0' Or (B.LeaveFlag='1' And B.LeaveDate>=  '<YMD/>' ))
AND NOT (('<YMD/> 23:59:59' < L.STARTLEAVEDATE) OR ('<YMD/> 00:00:00'> L.ENDLEAVEDATE))
AND D.ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
) AS [C10] --產假
,(
	select COUNT(*) AS  EmpInCount
from HR_EMPLOYEE as HE
LEFT OUTER JOIN VN_DEPARTMENT as d ON HE.DEPARTMENT_ID = d.ID
LEFT OUTER JOIN VN_UNIT as V ON HE.UNIT_ID = V.ID
JOIN PWERP_MS.dbo.RsKQResult A  On A.EmpCode=HE.EmpCode
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 LEFT JOIN hr.dbo.VN_LEAVECARD L On  L.EP_ID=HE.ID
  LEFT JOIN hr.dbo.VN_LHOLIDAY Y On  L.HD_ID=Y.ID
WHERE A.FDate=  '<YMD/>' 
and L.LEAVEAPPLY IN ('1')
and Y.HOLIDAYCLAS IN ('A','B','D','F','S','O')
and (B.LeaveFlag='0' Or (B.LeaveFlag='1' And B.LeaveDate>=  '<YMD/>' ))
AND NOT (('<YMD/> 23:59:59' < L.STARTLEAVEDATE) OR ('<YMD/> 00:00:00'> L.ENDLEAVEDATE))
AND D.ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
) AS [C11] --請假
,(
	select COUNT(*) AS  EmpInCount
from HR_EMPLOYEE as HE
LEFT OUTER JOIN VN_DEPARTMENT as d ON HE.DEPARTMENT_ID = d.ID
LEFT OUTER JOIN VN_UNIT as V ON HE.UNIT_ID = V.ID
JOIN PWERP_MS.dbo.RsKQResult A  On A.EmpCode=HE.EmpCode
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 LEFT JOIN hr.dbo.VN_LEAVECARD L On  L.EP_ID=HE.ID
 LEFT JOIN hr.dbo.VN_LHOLIDAY Y On  L.HD_ID=Y.ID
WHERE A.FDate=  '<YMD/>' 
and L.LEAVEAPPLY IN ('1')
and Y.HOLIDAYCLAS IN ('H')
and (B.LeaveFlag='0' Or (B.LeaveFlag='1' And B.LeaveDate>=  '<YMD/>' ))
AND NOT (('<YMD/> 23:59:59' < L.STARTLEAVEDATE) OR ('<YMD/> 00:00:00'> L.ENDLEAVEDATE))
AND D.ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
)  AS [C12] --年假
,(
SELECT  Count(distinct S.EMPLOYEENO) As EmpInCount 
FROM VN_EMPLOYEE_SUPPLEMENT S  
 JOIN hr.dbo.HR_EMPLOYEE E On S.EMPLOYEENO =E.ID
 JOIN hr.dbo.VN_UNIT V On  E.UNIT_ID=V.ID
 JOIN hr.dbo.VN_DEPARTMENT D On  E.DEPARTMENT_ID=D.ID
 JOIN PWERP_MS.dbo.RsEmployee B On E.EmpCode=B.EmpCode 
WHERE S.STARTDAY='<YMD/>' 
AND S.STATUS='L'
and (B.LeaveFlag='0' Or (B.LeaveFlag='1' And B.LeaveDate>=  '<YMD/>' ))
AND E.DEPARTMENT_ID=D.ID
AND V.UNIT=VU.UNIT
) AS [C13]--出差
,(
select COUNT(*) AS  EmpInCount
from HR_EMPLOYEE as HE
LEFT OUTER JOIN VN_DEPARTMENT as d ON HE.DEPARTMENT_ID = d.ID
LEFT OUTER JOIN VN_UNIT as V ON HE.UNIT_ID = V.ID
JOIN PWERP_MS.dbo.RsKQResult A  On A.EmpCode=HE.EmpCode
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 LEFT JOIN hr.dbo.VN_LEAVECARD L On  L.EP_ID=HE.ID
  LEFT JOIN hr.dbo.VN_LHOLIDAY Y On  L.HD_ID=Y.ID
WHERE A.FDate=  '<YMD/>' 
and L.LEAVEAPPLY IN ('1')
and Y.HOLIDAYCLAS IN ('I')
and (B.LeaveFlag='0' Or (B.LeaveFlag='1' And B.LeaveDate>=  '<YMD/>' ))
AND NOT (('<YMD/> 23:59:59' < L.STARTLEAVEDATE) OR ('<YMD/> 00:00:00'> L.ENDLEAVEDATE))
AND D.ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
) AS [C14]--工傷
,(
select A.EmpInCount-B.EmpInCount AS EmpInCount  from
(
select COUNT(*) AS  EmpInCount
from HR_EMPLOYEE as HE
LEFT OUTER JOIN VN_DEPARTMENT as d ON HE.DEPARTMENT_ID = d.ID
LEFT OUTER JOIN VN_UNIT as V ON HE.UNIT_ID = V.ID
JOIN PWERP_MS.dbo.RsKQResult A  On A.EmpCode=HE.EmpCode
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 LEFT JOIN hr.dbo.VN_LEAVECARD L On  L.EP_ID=HE.ID
WHERE A.FDate= '<YMD/>'
and (B.LeaveFlag='0' Or (B.LeaveFlag='1' And B.LeaveDate>=  '<YMD/>'))
AND (A.WorkFDate='  ' AND A.WorkEDate='  ')
AND D.ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
)A,
(
select COUNT(*) AS  EmpInCount
from HR_EMPLOYEE as HE
LEFT OUTER JOIN VN_DEPARTMENT as d ON HE.DEPARTMENT_ID = d.ID
LEFT OUTER JOIN VN_UNIT as V ON HE.UNIT_ID = V.ID
JOIN PWERP_MS.dbo.RsKQResult A  On A.EmpCode=HE.EmpCode
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 LEFT JOIN hr.dbo.VN_LEAVECARD L On  L.EP_ID=HE.ID
  LEFT JOIN hr.dbo.VN_LHOLIDAY Y On  L.HD_ID=Y.ID
WHERE A.FDate=  '<YMD/>' 
and L.LEAVEAPPLY IN ('1')
and Y.HOLIDAYCLAS IN ('I','A','B','D','F','S','O','H','E','G')
and (B.LeaveFlag='0' Or (B.LeaveFlag='1' And B.LeaveDate>=  '<YMD/>' ))
AND NOT (('<YMD/> 23:59:59' < L.STARTLEAVEDATE) OR ('<YMD/> 00:00:00'> L.ENDLEAVEDATE))
AND D.ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
)B
) AS [C15] --曠工
,(
select COUNT(*) AS  EmpInCount
from HR_EMPLOYEE as HE
LEFT OUTER JOIN VN_DEPARTMENT as d ON HE.DEPARTMENT_ID = d.ID
LEFT OUTER JOIN VN_UNIT as V ON HE.UNIT_ID = V.ID
JOIN PWERP_MS.dbo.RsKQResult A  On A.EmpCode=HE.EmpCode
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 LEFT JOIN hr.dbo.VN_LEAVECARD L On  L.EP_ID=HE.ID
   LEFT JOIN hr.dbo.VN_LHOLIDAY Y On  L.HD_ID=Y.ID
WHERE A.FDate=  '<YMD/>' 
and L.LEAVEAPPLY IN ('1')
and Y.HOLIDAYCLAS IN ('G')
and (B.LeaveFlag='0' Or (B.LeaveFlag='1' And B.LeaveDate>=  '<YMD/>' ))
AND NOT (('<YMD/> 23:59:59' < L.STARTLEAVEDATE) OR ('<YMD/> 00:00:00'> L.ENDLEAVEDATE))
AND D.ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
) AS [C16] --排休
,(SELECT   Count(distinct A.EmpCode) As EmpInCount 
FROM PWERP_MS.dbo.RsKQResult A  
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 JOIN hr.dbo.HR_EMPLOYEE E On  E.EMPLOYEENO=B.EmpID
 JOIN hr.dbo.VN_UNIT V On  E.UNIT_ID=V.ID
 JOIN hr.dbo.VN_DEPARTMENT D On  E.DEPARTMENT_ID=D.ID
 LEFT JOIN hr.dbo.VN_LEAVECARD L On  L.EP_ID=E.ID
WHERE A.FDate='<YMD/>'
and (B.LeaveFlag='0' Or (B.LeaveFlag='1' And B.LeaveDate>=  '<YMD/>' ))
and InDate=  '<YMD/>'
AND D.ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
) AS [C17]
,(SELECT  Count(distinct S.EMPLOYEENO) As EmpInCount 
FROM VN_EMPLOYEE_SUPPLEMENT S  
 JOIN hr.dbo.HR_EMPLOYEE E On S.EMPLOYEENO =E.ID
 JOIN hr.dbo.VN_UNIT V On  E.UNIT_ID=V.ID
 JOIN hr.dbo.VN_DEPARTMENT D On  E.DEPARTMENT_ID=D.ID
 JOIN PWERP_MS.dbo.RsEmployee B On E.EmpCode=B.EmpCode 
WHERE S.STARTDAY='<YMD/>' 
AND S.STATUS='R'
and (B.LeaveFlag='0' Or (B.LeaveFlag='1' And B.LeaveDate>=  '<YMD/>' ))
AND D.ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
) AS [C18]
,(SELECT Count(distinct A.EmpCode) As EmpInCount 
FROM PWERP_MS.dbo.RsKQResult A  
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 JOIN hr.dbo.HR_EMPLOYEE E On  E.EMPLOYEENO=B.EmpID
 JOIN hr.dbo.VN_UNIT V On  E.UNIT_ID=V.ID
 JOIN hr.dbo.VN_DEPARTMENT D On  E.DEPARTMENT_ID=D.ID
 LEFT JOIN hr.dbo.VN_LEAVECARD L On  L.EP_ID=E.ID
WHERE  B.LeaveFlag='1' 
and B.LeaveDate=   '<YMD/>' 
AND D.ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
) AS [C19]
,(SELECT isnull(V.SING,0)
FROM [hr].[dbo].[VN_UNIT] V
 JOIN hr.dbo.VN_DEPARTMENT D On  V.DEPARTMENT_ID=D.ID
AND D.ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
)  AS [SORT]
FROM [hr].[dbo].[VN_UNIT] VU
  INNER JOIN VN_DEPARTMENT VD 
  ON VD.ID=VU.DEPARTMENT_ID 
group by [DEPARTMENT_ID],[UNIT] with rollup  
order by [DEPARTMENT],[ROW]

UPDATE [hr].[dbo].[VN_YEAR_MONTH_PLANT_ATTENDANCE]
   SET SORT = '99'
   WHERE SORT IS NULL