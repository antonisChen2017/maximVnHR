Declare @AFDate DateTime,@AEDate DateTime
Set @AFDate=Convert(DateTime,'<year/>/<Month/>/01')
Set @AEDate=DateAdd(Day,-1,DateAdd(Month,1,Convert(DateTime,'<year/>/<Month/>/01')))
Declare @i int
Set @i=0
Declare @FFDate Char(10)
Set @FFDate=Convert(Char(10),@AFDate,111)

delete VN_YEAR_MONTH_LATE where YEAR='<year/>' and MONTH='<Month/>' and ISLATE='1'

Create Table #Tmp
(
YEAR nvarchar(10),
MONTH nvarchar(10),
EmpID Varchar(50),
CBName Varchar(50),
DeptCode Varchar(50),
DAYCOUNT Varchar(50),
ISLATE nvarchar(10),
lateETime Varchar(50)
)

Create Table #SAL
(
SSQL Varchar(4000),
lateCount Varchar(50),
lateH float,
lateM float
)

While @i<=DateDiff(Day,@AFDate,@AEDate)
	Begin
	
	declare @FDate DateTime =Convert(DateTime,@FFDate) 
	Insert Into #Tmp(YEAR ,MONTH,EmpID,CBName,DeptCode ,DAYCOUNT,ISLATE,lateETime)
	select  
   	'<year/>','<Month/>',E.EmpID,E.CBName,E.DeptCode,(@i+1),'1'
   	,CAST(ROUND(LTime,0) AS int)
	from   PWERP_MS.dbo.RsKQResult K,   PWERP_MS.dbo.RsEmployee E ,  PWERP_MS.dbo.RsBasTurn B
	Where  K.EmpCode=E.EmpCode
	AND K.Turn= B.Code
	AND FDate=@FDate
	And   K.WorkFTime<>'00:00'
	And SResult in ('0001','0003','0004','000A','000B') 
	And Convert(DateTime,@FDate+' '+TurnFTime)<=Convert(DateTime,@FDate+' '+'08:30')
	And datediff(n,(CONVERT(varchar(100),  @FDate, 23)+' '+K.TurnFTime),(CONVERT(varchar(100),  @FDate, 23)+' '+K.WorkFTime)) >0
	And  CAST(ROUND( LTime,0) AS int)>0
	Union All
	select 
		'<year/>','<Month/>',E.EmpID,E.CBName,E.DeptCode,(@i+1),'1'
	, CAST(ROUND(LTime,0) AS int)
	from   PWERP_MS.dbo.RsKQResult K,   PWERP_MS.dbo.RsEmployee E ,  PWERP_MS.dbo.RsBasTurn B
	Where  K.EmpCode=E.EmpCode
	AND K.Turn= B.Code
	AND FDate=@FDate
	And   K.WorkFTime<>'00:00'
	And SResult in ('0001','0003','0004','000A','000B') 
	And Convert(DateTime,@FDate+' '+TurnFTime)>Convert(DateTime,@FDate+' '+'11:30') And Convert(DateTime,@FDate+' '+TurnFTime)<=Convert(DateTime,@FDate+' '+'14:30')
	And datediff(n,(CONVERT(varchar(100),  @FDate, 23)+' '+K.TurnFTime),(CONVERT(varchar(100),  @FDate, 23)+' '+K.WorkFTime)) >0
	And  CAST(ROUND( LTime,0) AS int)>0
	Union All--把中餐的人加进来,这些人一定只排了一个班的
	select 
		'<year/>','<Month/>',E.EmpID,E.CBName,E.DeptCode,(@i+1),'1'
	, CAST(ROUND(LTime,0) AS int)
	from   PWERP_MS.dbo.RsKQResult K,   PWERP_MS.dbo.RsEmployee E ,  PWERP_MS.dbo.RsBasTurn B
	Where  K.EmpCode=E.EmpCode
	AND K.Turn= B.Code
	And   K.WorkFTime<>'00:00'
	And SResult in ('0001','0003','0004','000A','000B') 
	And Convert(DateTime,@FDate+' '+TurnFTime)<=Convert(DateTime,@FDate+' '+'08:30')
	And K.EmpCode In (Select EmpCode From  PWERP_MS.dbo.RsKQResult Where FDate=@FDate And Len(Turn)=1)
	And datediff(n,(CONVERT(varchar(100),  @FDate, 23)+' '+K.TurnFTime),(CONVERT(varchar(100),  @FDate, 23)+' '+K.WorkFTime)) >0
	And  CAST(ROUND( LTime,0) AS int)>0
	
	Set @FFDate=Convert(Char(10),DateAdd(Day,@i+1,@AFDate),111)
	Set @i=@i+1
End


--SELECT  Distinct  EmpID,CBName,DeptCode,YEAR,MONTH,ISLATE FROM #Tmp
--首先把有記錄的員工工號添加進來
Insert into VN_YEAR_MONTH_LATE(EMPLOYEENO,EMPLOYEE,DEPARTMENT,YEAR,MONTH,ISLATE) SELECT  Distinct  EmpID,CBName,DeptCode,YEAR,MONTH,ISLATE FROM #Tmp
--Drop Table #Tmp



--定义游标
Declare @SQL Varchar(4000)
declare @YEAR nvarchar(10),@MONTH nvarchar(10),@EmpID Varchar(50),@DAYCOUNT varchar(50),@lateETime varchar(50),@ISLATE nvarchar(10),@CBName Varchar(50)
declare Select_cursor cursor for
        select YEAR ,MONTH,EmpID,CBName,DAYCOUNT,ISLATE,lateETime  from #Tmp
open Select_cursor
fetch next from Select_cursor into @YEAR,@MONTH,@EmpID,@CBName ,@DAYCOUNT,@ISLATE,@lateETime  --提取操作的列数据放到局部变量中
while @@fetch_status=0      --返回被 FETCH 语句执行的最后游标的状态
/*
@@FETCH_STATUS =0          FETCH 语句成功
@@FETCH_STATUS =-1 FETCH 语句失败或此行不在结果集中
@@FETCH_STATUS =-2 被提取的行不存在
*/
        begin
                  --当表#Temp2列deptid存在相同的数据时，就直接在列username上追加@username值
                  if(exists(select EMPLOYEENO,EMPLOYEE,ISLATE from VN_YEAR_MONTH_LATE where EMPLOYEENO=@EmpID and ISLATE=@ISLATE and EMPLOYEE=@CBName and YEAR=@YEAR and MONTH=@MONTH)) 
                        -- update VN_YEAR_MONTH_LATE set DAY1=@lateETime where EMPLOYEENO=@EmpID and ISLATE=@ISLATE and EMPLOYEE=@CBName
                     Set @SQL=' update VN_YEAR_MONTH_LATE set DAY'+@DAYCOUNT+'='+@lateETime+' where EMPLOYEENO='+@EmpID+' and YEAR='+@YEAR+' and MONTH='+@MONTH+' and ISLATE='+@ISLATE+' and EMPLOYEE='''+@CBName+''''
					 
                  else 
                  --插入新数据
                     Set @SQL=' insert into VN_YEAR_MONTH_LATE (DAY'+@DAYCOUNT+') select lateETime from #Tmp where EmpID='+@EmpID+' and YEAR='+@YEAR+' and MONTH='+@MONTH+' and ISLATE='+@ISLATE+' and CBName='''+@CBName+''''
                  
                  
                  Exec (@SQL)
                  fetch next from Select_cursor into @YEAR,@MONTH,@EmpID,@CBName ,@DAYCOUNT,@ISLATE,@lateETime  --提取操作的列数据放到局部变量中
        end
close Select_cursor      
deallocate Select_cursor

declare @DAY1 nvarchar(10),@DAY2 nvarchar(10),@DAY3 nvarchar(10),@DAY4 nvarchar(10),@DAY5 nvarchar(10),@DAY6 nvarchar(10),@DAY7 nvarchar(10)
		,@DAY8 nvarchar(10),@DAY9 nvarchar(10),@DAY10 nvarchar(10),@DAY11 nvarchar(10),@DAY12 nvarchar(10),@DAY13 nvarchar(10),@DAY14 nvarchar(10)
		,@DAY15 nvarchar(10),@DAY16 nvarchar(10),@DAY17 nvarchar(10),@DAY18 nvarchar(10),@DAY19 nvarchar(10),@DAY20 nvarchar(10),@DAY21 nvarchar(10)
		,@DAY22 nvarchar(10),@DAY23 nvarchar(10),@DAY24 nvarchar(10),@DAY25 nvarchar(10),@DAY26 nvarchar(10),@DAY27 nvarchar(10),@DAY28 nvarchar(10)
		,@DAY29 nvarchar(10),@DAY30 nvarchar(10),@DAY31 nvarchar(10),@EMPLOYEENO nvarchar(50),@EMPLOYEE  nvarchar(50),@lateCount int
		,@lateTime int,@lateH int,@lateM int
declare Select_late cursor for
        select YEAR ,MONTH,ISLATE,EMPLOYEENO,EMPLOYEE,DAY1,DAY2,DAY3,DAY4,DAY5,DAY6,DAY7,DAY8,DAY9,DAY10,DAY11,DAY12,DAY13,DAY14,DAY15
        ,DAY16,DAY17,DAY18,DAY19,DAY20,DAY21,DAY22,DAY23,DAY24,DAY25,DAY26,DAY27,DAY28,DAY29,DAY30,DAY31 from VN_YEAR_MONTH_LATE
         WHERE YEAR='<year/>' and MONTH='<Month/>'  AND ISLATE='1'

open Select_late
fetch next from Select_late into @YEAR,@MONTH,@ISLATE,@EMPLOYEENO,@EMPLOYEE,@DAY1,@DAY2,@DAY3,@DAY4,@DAY5,@DAY6,@DAY7,@DAY8,@DAY9,@DAY10,@DAY11,@DAY12,@DAY13,@DAY14,@DAY15
        ,@DAY16,@DAY17,@DAY18,@DAY19,@DAY20,@DAY21,@DAY22,@DAY23,@DAY24,@DAY25,@DAY26,@DAY27,@DAY28,@DAY29,@DAY30,@DAY31  --提取操作的列数据放到局部变量中

   while @@fetch_status=0      --返回被 FETCH 语句执行的最后游标的状态
	  begin
	   Set @lateCount=0
	   Set @lateTime=0
	   Set @lateH=0
	   Set @lateM=0
	   if(@DAY1 is not null  and cast(@DAY1 as float)>0)
	    begin 
			Set @lateCount=@lateCount+1
			Set @lateTime=@lateTime+cast(@DAY1 as float)
		END
		
	   if(@DAY2 is not null   and cast(@DAY2 as float)>0)
	    begin 
			Set @lateCount=@lateCount+1
			Set @lateTime=@lateTime+cast(@DAY2 as float)
		END
	   if(@DAY3 is not null   and cast(@DAY3 as float)>0)
	    begin 
			Set @lateTime=@lateTime+cast(@DAY3 as float)
			Set @lateCount=@lateCount+1
		END
		
	   if(@DAY4 is not null   and cast(@DAY4 as float)>0)
	    begin 
			Set @lateCount=@lateCount+1
			Set @lateTime=@lateTime+cast(@DAY4 as float)
		END
	   if(@DAY5 is not null   and cast(@DAY5 as float)>0)
	    begin 
			Set @lateCount=@lateCount+1
			Set @lateTime=@lateTime+cast(@DAY5 as float)
		END
	   if(@DAY6 is not null   and cast(@DAY6 as float)>0)
	    begin 
			Set @lateCount=@lateCount+1
			Set @lateTime=@lateTime+cast(@DAY6 as float)
		END
	   if(@DAY7 is not null   and cast(@DAY7 as float)>0)
	    begin 
			Set @lateCount=@lateCount+1
			Set @lateTime=@lateTime+cast(@DAY7 as float)
		END
	   if(@DAY8 is not null   and cast(@DAY8 as float)>0)
		 begin 	
			Set @lateCount=@lateCount+1
			Set @lateTime=@lateTime+cast(@DAY8 as float)
		 END
	   if(@DAY9 is not null  and cast(@DAY9 as float)>0)
		begin 
			Set @lateCount=@lateCount+1
			Set @lateTime=@lateTime+cast(@DAY9 as float)
		END
	   if(@DAY10 is not null and cast(@DAY10 as float)>0)
	    begin 
			SET @lateTime=@lateTime+cast(@DAY10 as float)
			Set @lateCount=@lateCount+1 
		END

	   if(@DAY11 is not null and cast(@DAY11 as float)>0)
		begin 
			Set @lateCount=@lateCount+1
			Set @lateTime=@lateTime+cast(@DAY11 as float)
		END
	   if(@DAY12 is not null  and cast(@DAY12 as float)>0)
		begin 
			Set @lateCount=@lateCount+1
			Set @lateTime=@lateTime+cast(@DAY12 as float)
		END
	   if(@DAY13 is not null  and cast(@DAY13 as float)>0)
		begin 
			Set @lateCount=@lateCount+1
			Set @lateTime=@lateTime+cast(@DAY13 as float)
		END
	   if(@DAY14 is not null and cast(@DAY14 as float)>0)
		begin
			Set @lateCount=@lateCount+1
			Set @lateTime=@lateTime+cast(@DAY14 as float)
		END
	   if(@DAY15 is not null and cast(@DAY15 as float)>0)
		begin
			Set @lateCount=@lateCount+1
			Set @lateTime=@lateTime+cast(@DAY15 as float)
		END
	   if(@DAY16 is not null and cast(@DAY16 as float)>0)
		begin
			Set @lateCount=@lateCount+1
			Set @lateTime=@lateTime+cast(@DAY16 as float)
		END
	   if(@DAY17 is not null and cast(@DAY17 as float)>0)
		begin
			Set @lateCount=@lateCount+1
			Set @lateTime=@lateTime+cast(@DAY17 as float)
		END
	   if(@DAY18 is not null and cast(@DAY18 as float)>0)
		begin
			Set @lateCount=@lateCount+1
			Set @lateTime=@lateTime+cast(@DAY18 as float)
		END
	   if(@DAY19 is not null and cast(@DAY19 as float)>0)
		begin
			Set @lateCount=@lateCount+1
			Set @lateTime=@lateTime+cast(@DAY19 as float)
		END
		
	   if(@DAY20 is not null and cast(@DAY20 as float)>0)
		begin
			Set @lateCount=@lateCount+1
			Set @lateTime=@lateTime+cast(@DAY20 as float)
		END
		
	   if(@DAY21 is not null and cast(@DAY21 as float)>0)
		begin
			Set @lateCount=@lateCount+1
			Set @lateTime=@lateTime+cast(@DAY21 as float)
		END
		
	   if(@DAY22 is not null and cast(@DAY22 as float)>0)
		begin
			Set @lateCount=@lateCount+1
			Set @lateTime=@lateTime+cast(@DAY22 as float)
		END
		
	   if(@DAY23 is not null and cast(@DAY23 as float)>0)
		begin
			Set @lateCount=@lateCount+1
			Set @lateTime=@lateTime+cast(@DAY23 as float)
		END
		
	   if(@DAY24 is not null and cast(@DAY24 as float)>0)
		begin
			Set @lateCount=@lateCount+1
			Set @lateTime=@lateTime+cast(@DAY24 as float)
		END
		
	   if(@DAY25 is not null and cast(@DAY25 as float)>0)
		begin
			Set @lateCount=@lateCount+1
			Set @lateTime=@lateTime+cast(@DAY25 as float)
		END
		
	   if(@DAY26 is not null and cast(@DAY26 as float)>0)
		begin
			Set @lateCount=@lateCount+1
			Set @lateTime=@lateTime+cast(@DAY26 as float)
		END
	   if(@DAY27 is not null and cast(@DAY27 as float)>0)
		begin
			Set @lateCount=@lateCount+1
			Set @lateTime=@lateTime+cast(@DAY27 as float)
		END
		
	   if(@DAY28 is not null and cast(@DAY28 as float)>0)
		begin
			Set @lateCount=@lateCount+1
			Set @lateTime=@lateTime+cast(@DAY28 as float)
		END
		
	   if(@DAY29 is not null and cast(@DAY29 as float)>0)
		begin
			Set @lateCount=@lateCount+1
			Set @lateTime=@lateTime+cast(@DAY29 as float)
		END
		
	   if(@DAY30 is not null and cast(@DAY30 as float)>0)
		begin
			Set @lateCount=@lateCount+1
			Set @lateTime=@lateTime+cast(@DAY30 as float)
		END
		
	   if(@DAY31 is not null and cast(@DAY31 as float)>0)
		begin
			Set @lateCount=@lateCount+1
			Set @lateTime=@lateTime+cast(@DAY31 as float)
		END
		
		if(@lateTime<60)
		begin
			Set @lateH=0
			Set @lateM=@lateTime
		END
		
		if(@lateTime>=60)
		begin
			Set @lateH=@lateTime/60
			Set @lateM=floor(@lateTime%60)
		END
	    Set @SQL=' update VN_YEAR_MONTH_LATE set LATETIMES='+convert(varchar(20),@lateCount)+' , HOUR='+cast(@lateH as nvarchar(10))+' ,MINUTE='+cast(@lateM as nvarchar(10))+' where EMPLOYEENO='''+@EMPLOYEENO+''' and YEAR='+@YEAR+' and MONTH='+@MONTH+' and ISLATE=1'
	  
	    Insert Into #SAL(SSQL ,lateCount,lateH,lateM)VALUES(@SQL,@lateCount,@lateH,@lateM)
	  
	   Exec (@SQL)
      fetch next from Select_late into @YEAR,@MONTH,@ISLATE,@EMPLOYEENO,@EMPLOYEE,@DAY1,@DAY2,@DAY3,@DAY4,@DAY5,@DAY6,@DAY7,@DAY8,@DAY9,@DAY10,@DAY11,@DAY12,@DAY13,@DAY14,@DAY15
        ,@DAY16,@DAY17,@DAY18,@DAY19,@DAY20,@DAY21,@DAY22,@DAY23,@DAY24,@DAY25,@DAY26,@DAY27,@DAY28,@DAY29,@DAY30,@DAY31  --提取操作的列数据放到局部变量中
	  end
close Select_late      
deallocate Select_late

    IF('<year/>'=(SELECT DATENAME (YEAR ,getdate())))
	   begin
	    IF('<Month/>'<(SELECT DATENAME (MONTH ,getdate())))
			begin
				INSERT INTO [hr].[dbo].[VN_ISQUERY_LATE]([YEAR],[MONTH] ,[ISLATE]) VALUES ('<year/>','<Month/>','1')
			END
	END
	
	IF('<year/>'<(SELECT DATENAME (YEAR ,getdate())))
	   begin
	   INSERT INTO [hr].[dbo].[VN_ISQUERY_LATE]([YEAR],[MONTH] ,[ISLATE]) VALUES ('<year/>','<Month/>','1')
	END

--SELECT * FROM #Tmp
Drop Table #Tmp
SELECT * FROM #SAL
Drop Table #SAL