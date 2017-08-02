delete from HR_EMPLOYEE where ID in
(select ID from HR_EMPLOYEE  group by ID having count(1) > 1)

INSERT INTO [hr].[dbo].[HR_EMPLOYEE]
           ([ID]
           ,[EMPLOYEENO]
           ,[EMPLOYEE]
           ,[UNIT_ID]
           ,[DEPARTMENT_ID]
           ,[VIETNAMESE]
           ,[ENTRYDATE]
           ,[DUTIES]
           ,[ROLE],
           Turn,
           TurnFTime,
           TurnETime,
           EmpCode)
    select [ID],[EMPLOYEENO],[EMPLOYEE],[UNIT_ID],[DEPARTMENT_ID],[VIETNAMESE],[ENTRYDATE],[DUTIES],[ROLE],Turn,TurnFTime,TurnETime,EmpCode from VN_EMPLOYEE
WHERE NOT EXISTS(SELECT [EMPLOYEENO]
                    FROM HR_EMPLOYEE
                   WHERE VN_EMPLOYEE.[EMPLOYEENO] = HR_EMPLOYEE.[EMPLOYEENO])
                   
update [hr].[dbo].[HR_EMPLOYEE] SET [ROLE]=T2.ROLE
FROM [HR_EMPLOYEE] AS T1,VN_EMPLOYEE_UNIT AS T2
WHERE  (t1.EMPLOYEENO=t2.EMPLOYEENO and t1.ROLE<>t2.ROLE)

update [hr].[dbo].[HR_EMPLOYEE] SET DEPARTMENT_ID=T2.DEPARTMENT_ID
FROM [HR_EMPLOYEE] AS T1,VN_EMPLOYEE AS T2
WHERE  (t1.EMPLOYEENO=t2.EMPLOYEENO and t1.DEPARTMENT_ID<>t2.DEPARTMENT_ID)

--delete from HR_EMPLOYEE where EmpCode in
--(select  B.EmpCode
--from HR_EMPLOYEE as HE
--LEFT OUTER JOIN VN_DEPARTMENT as d ON HE.DEPARTMENT_ID = d.ID
--LEFT OUTER JOIN VN_UNIT as V ON HE.UNIT_ID = V.ID
--JOIN PWERP_MS.dbo.RsKQResult A  On A.EmpCode=HE.EmpCode
-- JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
--WHERE   B.LeaveFlag='1')


