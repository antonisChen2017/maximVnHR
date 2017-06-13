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