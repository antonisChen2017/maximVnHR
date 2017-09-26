--避免有相同人員出現
delete from HR_EMPLOYEE where ID in
(select ID from HR_EMPLOYEE  group by ID having count(1) > 1)
--複製新近人員
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
           EmpCode
           ,Sex)
    select [ID],[EMPLOYEENO],[EMPLOYEE],[UNIT_ID],[DEPARTMENT_ID],[VIETNAMESE],[ENTRYDATE],[DUTIES],[ROLE],Turn,TurnFTime,TurnETime,EmpCode,Sex from VN_EMPLOYEE
WHERE NOT EXISTS(SELECT [EMPLOYEENO]
                    FROM HR_EMPLOYEE
                   WHERE VN_EMPLOYEE.[EMPLOYEENO] = HR_EMPLOYEE.[EMPLOYEENO])
 --複製角色                 
update [hr].[dbo].[HR_EMPLOYEE] SET [ROLE]=T2.ROLE
FROM [HR_EMPLOYEE] AS T1,VN_EMPLOYEE_UNIT AS T2
WHERE  (t1.EMPLOYEENO=t2.EMPLOYEENO and t1.ROLE<>t2.ROLE)
--得到新人部門
update [hr].[dbo].[HR_EMPLOYEE] SET DEPARTMENT_ID=T2.DEPARTMENT_ID
FROM [HR_EMPLOYEE] AS T1,VN_EMPLOYEE AS T2
WHERE  (t1.EMPLOYEENO=t2.EMPLOYEENO and t1.DEPARTMENT_ID<>t2.DEPARTMENT_ID)
--得到新人性別
update [hr].[dbo].[HR_EMPLOYEE] SET Sex=T2.Sex
FROM [HR_EMPLOYEE] AS T1,VN_EMPLOYEE AS T2
WHERE  (t1.EMPLOYEENO=t2.EMPLOYEENO and t1.Sex IS NULL)
--預設新人為員工
update [hr].[dbo].[HR_EMPLOYEE] SET ROLE='E'
FROM [HR_EMPLOYEE] AS T1
WHERE  ( t1.ROLE IS NULL)

--預設(推測)新人單位,使用職稱

--預設有單位有職稱者自動產生登入帳號
INSERT INTO [jj].[dbo].[VH_USER]
           ([LOGIN]
           ,[PASSWORD]
           ,[NAME]
           ,[DISPLAY]
           ,[PHONE]
           ,[SEX]
           ,[PORTAL_KEY]
           ,[STATUS]
           ,[EMP_NO]
           ,[EMP_NO1]
           ,[EMAIL]
           ,[FAX],
           [ADDRESS])
SELECT [EMPLOYEENO]
      ,'48b1caea'
      ,E.EMPLOYEE
      ,E.EMPLOYEE
      ,[EMPLOYEENO]
      ,'F'
      ,'AT'
      ,'R'
      ,D.DEPARTMENT
      ,U.UNIT
      ,''
      ,'build'
      ,E.ROLE
  FROM [hr].[dbo].[HR_EMPLOYEE] E
JOIN VN_UNIT U
ON E.UNIT_ID=U.ID
JOIN VN_DEPARTMENT D
ON E.DEPARTMENT_ID=D.ID
WHERE NOT EXISTS(SELECT [EMPLOYEENO]
  FROM jj.dbo.VH_USER
  WHERE VH_USER.PHONE =  E.[EMPLOYEENO])
  
  
  
  INSERT INTO [jj].[dbo].[VH_USER_ROLE]
(LOGIN
,ROLE)
select 
[LOGIN],
CASE WHEN [ADDRESS] IN ('E') THEN 'az.emp'
     WHEN [ADDRESS] IN ('U') THEN 'un.mas'
     WHEN [ADDRESS] IN ('D') THEN 'dt.mas'
     WHEN [ADDRESS] IN ('M') THEN 'lo.mas'
     WHEN [ADDRESS] IN ('B') THEN 'dg.mas'
     WHEN [ADDRESS] IN ('G') THEN 'gr.mas' 
     ELSE 'az.emp' 
END
from [hr].[dbo].[HR_EMPLOYEE] E
JOIN VN_UNIT U
ON E.UNIT_ID=U.ID
JOIN VN_DEPARTMENT D
ON E.DEPARTMENT_ID=D.ID
JOIN [jj].[dbo].[VH_USER] VU
ON VU.LOGIN=E.EMPLOYEENO
WHERE NOT EXISTS(SELECT [EMPLOYEENO]
  FROM jj.dbo.[VH_USER_ROLE]
  WHERE VH_USER_ROLE.LOGIN =  E.[EMPLOYEENO])
AND [FAX]='build'



INSERT INTO [jj].[dbo].[VH_USER_PORTAL]
(LOGIN
,PORTAL_KEY)
select 
[LOGIN],
'AT'
from [hr].[dbo].[HR_EMPLOYEE] E
JOIN VN_UNIT U
ON E.UNIT_ID=U.ID
JOIN VN_DEPARTMENT D
ON E.DEPARTMENT_ID=D.ID
JOIN [jj].[dbo].[VH_USER] VU
ON VU.LOGIN=E.EMPLOYEENO
WHERE NOT EXISTS(SELECT [EMPLOYEENO]
  FROM jj.dbo.[VH_USER_PORTAL]
  WHERE [VH_USER_PORTAL].LOGIN =  E.[EMPLOYEENO])
AND [FAX]='build'



