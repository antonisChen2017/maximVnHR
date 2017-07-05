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
	SELECT Count(distinct A.EmpCode) As EmpInCount  
FROM PWERP_MS.dbo.RsKQResult A  
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 JOIN hr.dbo.HR_EMPLOYEE E On  E.EMPLOYEENO=B.EmpID
 JOIN hr.dbo.VN_UNIT V On  E.UNIT_ID=V.ID
 JOIN hr.dbo.VN_DEPARTMENT D On  E.DEPARTMENT_ID=D.ID
WHERE  A.FDate='<YMD/>'
and (LeaveFlag='0' Or (LeaveFlag='1' And LeaveDate>='<YMD/>'))
AND B.Nation<>'0002' 
AND V.DEPARTMENT_ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
	) AS [C1]
	,(
SELECT Count(distinct A.EmpCode) As EmpInCount
FROM PWERP_MS.dbo.RsKQResult A  
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 JOIN hr.dbo.HR_EMPLOYEE E On  E.EMPLOYEENO=B.EmpID
 JOIN hr.dbo.VN_UNIT V On  E.UNIT_ID=V.ID
 JOIN hr.dbo.VN_DEPARTMENT D On  E.DEPARTMENT_ID=D.ID
WHERE  A.FDate='2017/02/09'
AND B.Nation='0002' 
and (LeaveFlag='0' Or (LeaveFlag='1' And LeaveDate>='<YMD/>'))
AND V.DEPARTMENT_ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
	) AS [C2]
		,(
SELECT Count(distinct A.EmpCode) As EmpInCount
FROM PWERP_MS.dbo.RsKQResult A  
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 JOIN hr.dbo.HR_EMPLOYEE E On  E.EMPLOYEENO=B.EmpID
 JOIN hr.dbo.VN_UNIT V On  E.UNIT_ID=V.ID
 JOIN hr.dbo.VN_DEPARTMENT D On  E.DEPARTMENT_ID=D.ID
WHERE  A.FDate='<YMD/>'
AND B.Nation='0002' 
and (LeaveFlag='0' Or (LeaveFlag='1' And LeaveDate>='<YMD/>'))
AND V.DEPARTMENT_ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT) 
AS [C3]
	,(SELECT Count(distinct A.EmpCode) As EmpInCount
FROM PWERP_MS.dbo.RsKQResult A  
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 JOIN hr.dbo.HR_EMPLOYEE E On  E.EMPLOYEENO=B.EmpID
 JOIN hr.dbo.VN_UNIT V On  E.UNIT_ID=V.ID
 JOIN hr.dbo.VN_DEPARTMENT D On  E.DEPARTMENT_ID=D.ID
WHERE A.FDate='<YMD/>'
AND B.Nation='0002' 
and (LeaveFlag='0' Or (LeaveFlag='1' And LeaveDate>='<YMD/>'))
AND (A.WorkFTime<>'00:00' OR A.WorkETime<>'<YMD/>')
AND V.DEPARTMENT_ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
) AS [C4]
,(SELECT Count(distinct A.EmpCode) As EmpInCount
FROM PWERP_MS.dbo.RsKQResult A  
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 JOIN hr.dbo.HR_EMPLOYEE E On  E.EMPLOYEENO=B.EmpID
 JOIN hr.dbo.VN_UNIT V On  E.UNIT_ID=V.ID
 JOIN hr.dbo.VN_DEPARTMENT D On  E.DEPARTMENT_ID=D.ID
WHERE A.FDate='<YMD/>'
and (LeaveFlag='0' Or (LeaveFlag='1' And LeaveDate>='<YMD/>'))
and A.Turn='A1'
AND (A.WorkFTime<>'00:00' and A.WorkETime<>'00:00')
AND V.DEPARTMENT_ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
) AS [C5]
,(SELECT Count(distinct A.EmpCode) As EmpInCount
FROM PWERP_MS.dbo.RsKQResult A  
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 JOIN hr.dbo.HR_EMPLOYEE E On  E.EMPLOYEENO=B.EmpID
 JOIN hr.dbo.VN_UNIT V On  E.UNIT_ID=V.ID
 JOIN hr.dbo.VN_DEPARTMENT D On  E.DEPARTMENT_ID=D.ID
WHERE A.FDate='<YMD/>'
and (LeaveFlag='0' Or (LeaveFlag='1' And LeaveDate>='<YMD/>'))
and A.Turn='C1'
AND (A.WorkFTime<>'00:00' and A.WorkETime<>'00:00')
AND V.DEPARTMENT_ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
) AS [C6]
,(SELECT Count(distinct A.EmpCode) As EmpInCount
FROM PWERP_MS.dbo.RsKQResult A  
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 JOIN hr.dbo.HR_EMPLOYEE E On  E.EMPLOYEENO=B.EmpID
 JOIN hr.dbo.VN_UNIT V On  E.UNIT_ID=V.ID
 JOIN hr.dbo.VN_DEPARTMENT D On  E.DEPARTMENT_ID=D.ID
WHERE A.FDate='<YMD/>'
and (LeaveFlag='0' Or (LeaveFlag='1' And LeaveDate>='<YMD/>'))
and A.Turn='C2'
AND (A.WorkFTime<>'00:00' and A.WorkETime<>'00:00')
AND V.DEPARTMENT_ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
)  AS [C7]
,(
SELECT Count(distinct A.EmpCode) As EmpInCount
FROM PWERP_MS.dbo.RsKQResult A  
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 JOIN hr.dbo.HR_EMPLOYEE E On  E.EMPLOYEENO=B.EmpID
 JOIN hr.dbo.VN_UNIT V On  E.UNIT_ID=V.ID
 JOIN hr.dbo.VN_DEPARTMENT D On  E.DEPARTMENT_ID=D.ID
WHERE A.FDate='<YMD/>'
and (LeaveFlag='0' Or (LeaveFlag='1' And LeaveDate>='<YMD/>'))
and A.Turn='C3'
AND (A.WorkFTime<>'00:00' and A.WorkETime<>'00:00')
AND V.DEPARTMENT_ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
) AS [C8]
,(
SELECT Count(distinct A.EmpCode) As EmpInCount
FROM PWERP_MS.dbo.RsKQResult A  
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 JOIN hr.dbo.HR_EMPLOYEE E On  E.EMPLOYEENO=B.EmpID
 JOIN hr.dbo.VN_UNIT V On  E.UNIT_ID=V.ID
 JOIN hr.dbo.VN_DEPARTMENT D On  E.DEPARTMENT_ID=D.ID
WHERE A.FDate='<YMD/>'
and (LeaveFlag='0' Or (LeaveFlag='1' And LeaveDate>='<YMD/>'))
and A.Turn IN ('CD','CD1','CN')
AND (A.WorkFTime<>'00:00' and A.WorkETime<>'00:00')
AND V.DEPARTMENT_ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
) AS [C9]
,(SELECT   Count(distinct A.EmpCode) As EmpInCount
FROM PWERP_MS.dbo.RsKQResult A  
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 JOIN hr.dbo.HR_EMPLOYEE E On  E.EMPLOYEENO=B.EmpID
 JOIN hr.dbo.VN_UNIT V On  E.UNIT_ID=V.ID
 JOIN hr.dbo.VN_DEPARTMENT D On  E.DEPARTMENT_ID=D.ID
 LEFT JOIN hr.dbo.VN_LEAVECARD L On  L.EP_ID=E.ID
WHERE A.FDate=  '<YMD/>' 
and L.LEAVEAPPLY IN ('1')
and L.HD_ID IN ('5')
and (LeaveFlag='0' Or (LeaveFlag='1' And LeaveDate>=  '<YMD/>' ))
AND L.STARTLEAVEDATE>=  '<YMD/>  00:00:00'
AND L.ENDLEAVEDATE<='<YMD/> 23:59:59'
AND V.DEPARTMENT_ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
) AS [C10]
,(SELECT   Count(distinct A.EmpCode) As EmpInCount
FROM PWERP_MS.dbo.RsKQResult A  
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 JOIN hr.dbo.HR_EMPLOYEE E On  E.EMPLOYEENO=B.EmpID
 JOIN hr.dbo.VN_UNIT V On  E.UNIT_ID=V.ID
 JOIN hr.dbo.VN_DEPARTMENT D On  E.DEPARTMENT_ID=D.ID
 LEFT JOIN hr.dbo.VN_LEAVECARD L On  L.EP_ID=E.ID
WHERE A.FDate=  '<YMD/>' 
and L.LEAVEAPPLY IN ('1')
and L.HD_ID IN ('1','2','4','6','10')
and (LeaveFlag='0' Or (LeaveFlag='1' And LeaveDate>=  '<YMD/>' ))
AND L.STARTLEAVEDATE>=  '<YMD/>  00:00:00'
AND L.ENDLEAVEDATE<='<YMD/> 23:59:59'
AND V.DEPARTMENT_ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
) AS [C11]
,(SELECT   Count(distinct A.EmpCode) As EmpInCount
FROM PWERP_MS.dbo.RsKQResult A  
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 JOIN hr.dbo.HR_EMPLOYEE E On  E.EMPLOYEENO=B.EmpID
 JOIN hr.dbo.VN_UNIT V On  E.UNIT_ID=V.ID
 JOIN hr.dbo.VN_DEPARTMENT D On  E.DEPARTMENT_ID=D.ID
 LEFT JOIN hr.dbo.VN_LEAVECARD L On  L.EP_ID=E.ID
WHERE A.FDate=  '<YMD/>' 
and L.LEAVEAPPLY IN ('1')
and L.HD_ID IN ('8')
and (LeaveFlag='0' Or (LeaveFlag='1' And LeaveDate>=  '<YMD/>' ))
AND L.STARTLEAVEDATE>=  '<YMD/>  00:00:00'
AND L.ENDLEAVEDATE<='<YMD/> 23:59:59'
AND V.DEPARTMENT_ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
)  AS [C12]
,(SELECT  Count(distinct S.EMPLOYEENO) As EmpInCount 
FROM VN_EMPLOYEE_SUPPLEMENT S  
 JOIN hr.dbo.HR_EMPLOYEE E On S.EMPLOYEENO =E.ID
 JOIN hr.dbo.VN_UNIT V On  E.UNIT_ID=V.ID
 JOIN hr.dbo.VN_DEPARTMENT D On  E.DEPARTMENT_ID=D.ID
 JOIN PWERP_MS.dbo.RsEmployee B On E.EmpCode=B.EmpCode 
WHERE S.STARTDAY='<YMD/>' 
AND S.STATUS='L'
and (LeaveFlag='0' Or (LeaveFlag='1' And LeaveDate>=  '<YMD/>' ))
AND E.DEPARTMENT_ID=D.ID
AND V.UNIT=VU.UNIT
) AS [C13]
,(SELECT   Count(distinct A.EmpCode) As EmpInCount
FROM PWERP_MS.dbo.RsKQResult A  
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 JOIN hr.dbo.HR_EMPLOYEE E On  E.EMPLOYEENO=B.EmpID
 JOIN hr.dbo.VN_UNIT V On  E.UNIT_ID=V.ID
 JOIN hr.dbo.VN_DEPARTMENT D On  E.DEPARTMENT_ID=D.ID
 LEFT JOIN hr.dbo.VN_LEAVECARD L On  L.EP_ID=E.ID
WHERE A.FDate=  '<YMD/>' 
and L.LEAVEAPPLY IN ('1')
and L.HD_ID IN ('3')
and (LeaveFlag='0' Or (LeaveFlag='1' And LeaveDate>=  '<YMD/>' ))
AND L.STARTLEAVEDATE>=  '<YMD/>  00:00:00'
AND L.ENDLEAVEDATE<='<YMD/> 23:59:59'
AND V.DEPARTMENT_ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
) AS [C14]
,(SELECT   Count(distinct A.EmpCode) As EmpInCount 
FROM PWERP_MS.dbo.RsKQResult A  
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 JOIN hr.dbo.HR_EMPLOYEE E On  E.EMPLOYEENO=B.EmpID
 JOIN hr.dbo.VN_UNIT V On  E.UNIT_ID=V.ID
 JOIN hr.dbo.VN_DEPARTMENT D On  E.DEPARTMENT_ID=D.ID
 LEFT JOIN hr.dbo.VN_LEAVECARD L On  L.EP_ID=E.ID
WHERE A.FDate= '<YMD/>'
and (LeaveFlag='0' Or (LeaveFlag='1' And LeaveDate>=  '<YMD/>'))
AND (A.WorkFDate='  ' AND A.WorkEDate='  ')
AND V.DEPARTMENT_ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
) AS [C15]
,(SELECT   Count(distinct A.EmpCode) As EmpInCount
FROM PWERP_MS.dbo.RsKQResult A  
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 JOIN hr.dbo.HR_EMPLOYEE E On  E.EMPLOYEENO=B.EmpID
 JOIN hr.dbo.VN_UNIT V On  E.UNIT_ID=V.ID
 JOIN hr.dbo.VN_DEPARTMENT D On  E.DEPARTMENT_ID=D.ID
 LEFT JOIN hr.dbo.VN_LEAVECARD L On  L.EP_ID=E.ID
WHERE A.FDate=  '<YMD/>' 
and L.LEAVEAPPLY IN ('1')
and L.HD_ID IN ('7')
and (LeaveFlag='0' Or (LeaveFlag='1' And LeaveDate>=  '<YMD/>' ))
AND L.STARTLEAVEDATE>=  '<YMD/>  00:00:00'
AND L.ENDLEAVEDATE<='<YMD/> 23:59:59'
AND V.DEPARTMENT_ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
) AS [C16]
,(SELECT   Count(distinct A.EmpCode) As EmpInCount 
FROM PWERP_MS.dbo.RsKQResult A  
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 JOIN hr.dbo.HR_EMPLOYEE E On  E.EMPLOYEENO=B.EmpID
 JOIN hr.dbo.VN_UNIT V On  E.UNIT_ID=V.ID
 JOIN hr.dbo.VN_DEPARTMENT D On  E.DEPARTMENT_ID=D.ID
 LEFT JOIN hr.dbo.VN_LEAVECARD L On  L.EP_ID=E.ID
WHERE A.FDate='<YMD/>'
and (LeaveFlag='0' Or (LeaveFlag='1' And LeaveDate>=  '<YMD/>' ))
and InDate=  '<YMD/>'
AND V.DEPARTMENT_ID=VU.DEPARTMENT_ID
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
and (LeaveFlag='0' Or (LeaveFlag='1' And LeaveDate>=  '<YMD/>' ))
AND E.DEPARTMENT_ID=D.ID
AND V.UNIT=VU.UNIT
) AS [C18]
,(SELECT Count(distinct A.EmpCode) As EmpInCount 
FROM PWERP_MS.dbo.RsKQResult A  
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 JOIN hr.dbo.HR_EMPLOYEE E On  E.EMPLOYEENO=B.EmpID
 JOIN hr.dbo.VN_UNIT V On  E.UNIT_ID=V.ID
 JOIN hr.dbo.VN_DEPARTMENT D On  E.DEPARTMENT_ID=D.ID
 LEFT JOIN hr.dbo.VN_LEAVECARD L On  L.EP_ID=E.ID
WHERE  LeaveFlag='1' 
and LeaveDate=   '<YMD/>' 
AND V.DEPARTMENT_ID=VU.DEPARTMENT_ID
AND V.UNIT=VU.UNIT
) AS [C19]
,(SELECT isnull(V.SING,0)
FROM [hr].[dbo].[VN_UNIT] V
 JOIN hr.dbo.VN_DEPARTMENT D On  V.DEPARTMENT_ID=D.ID
AND V.DEPARTMENT_ID=VU.DEPARTMENT_ID
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