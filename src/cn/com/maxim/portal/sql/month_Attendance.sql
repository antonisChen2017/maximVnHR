Declare @FDate DateTime,@EDate DateTime
Set @FDate=Convert(DateTime,'<year/>/<Month/>/01')
Set @EDate=DateAdd(Day,-1,DateAdd(Month,1,Convert(DateTime,'<year/>/<Month/>/01')))

Truncate Table PWERP_MS.dbo.Tmp_RSKQMonthDay

delete VN_YEAR_MONTH_ATTENDANCE where YEAR='<year/>' and MONTH='<Month/>'  and  DEPARTMENT='<DEPARTMENT_ID/>'
and UNIT='<UNIT_ID/>'


Create Table #Tmp
(
FDate Datetime,
EmpCode Varchar(50),
ZBHour Decimal(18,2),
OTHour Decimal(18,2),
OTTHour Decimal(18,2)
)
Create Table #SAL(

SSQL Varchar(4000),
lateCount Varchar(20)
)
Create Table #All
(
	[EmpCode] [varchar] (50)  NULL ,
	
	[ZBHour1] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour1] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour1] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour2] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour2] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour2] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour3] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour3] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour3] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour4] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour4] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour4] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour5] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour5] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour5] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour6] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour6] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour6] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour7] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour7] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour7] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour8] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour8] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour8] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour9] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour9] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour9] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour10] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour10] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour10] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour11] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour11] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour11] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour12] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour12] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour12] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour13] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour13] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour13] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour14] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour14] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour14] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour15] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour15] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour15] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour16] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour16] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour16] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour17] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour17] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour17] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour18] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour18] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour18] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour19] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour19] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour19] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour20] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour20] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour20] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour21] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour21] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour21] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour22] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour22] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour22] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour23] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour23] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour23] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour24] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour24] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour24] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour25] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour25] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour25] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour26] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour26] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour26] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour27] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour27] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour27] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour28] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour28] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour28] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour29] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour29] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour29] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour30] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour30] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour30] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour31] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour31] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour31] [decimal](18, 2) NULL  DEFAULT (0),

	[ZBHour] [decimal](18, 2) NULL  DEFAULT (0),	
	[OTHour] [decimal](18, 2) NULL  DEFAULT (0),
	[OTTHour] [decimal](18, 2) NULL  DEFAULT (0),
	QTime [decimal](18, 2) NULL  DEFAULT (0),QTimeSalary  [decimal](18, 2) NULL  DEFAULT (0)
)

--所有考勤記錄
Insert Into #Tmp(FDate,EmpCode,ZBHour,OTHour,OTTHour)
Select A.FDate,A.EmpCode,A.ZBMin/60.0 , (A.OTMin+IsNull(B.ZBMin,0)+IsNull(B.OTMin,0))/60.0, (A.OTTMin+IsNull(B.OTTMin,0))/60.0 From
(Select FDate,EmpCode,ZBMin=Sum(AllTime), OTMin=Sum(OTime+AutoOTTime), OTTMin=Sum(OTTime) From PWERP_MS.dbo.RsKQResult  Where FDate Between @FDate And @EDate And Turn not in (Select Code From PWERP_MS.dbo.RSBasTurn Where EveFlag='A')  Group By FDate,EmpCode) A
Left Join
(Select FDate,EmpCode,ZBMin=Sum(AllTime), OTMin=Sum(OTime+AutoOTTime), OTTMin=Sum(OTTime) From PWERP_MS.dbo.RsKQResult  Where FDate Between @FDate And @EDate And Turn  in (Select Code From PWERP_MS.dbo.RSBasTurn Where EveFlag='A')  Group By FDate,EmpCode) B
On A.FDate=B.FDate And A.EmpCode=B.EmpCode
--------------这里系统设置情况如下:>=0.25 <=0.74 的为0.5,>=0.75 <=0.99 的为 1,其它情况为0
Update #Tmp Set 
ZBHour=Case When Convert(int,right(Convert(varchar(20),Convert(Decimal(18,2),ZBHour)),2))  Between 25 and 74  Then Convert(int ,ZBHour)+0.5  Else Case When  Convert(int,right(Convert(varchar(20),Convert(Decimal(18,2),ZBHour)),2))   Between 75 and 99  Then Convert(int ,ZBHour)+1 Else  Convert(int ,ZBHour) End End ,
OTHour=Case When Convert(int,right(Convert(varchar(20),Convert(Decimal(18,2),OTHour)),2))  Between 25 and 74  Then Convert(int ,OTHour)+0.5  Else Case When  Convert(int,right(Convert(varchar(20),Convert(Decimal(18,2),OTHour)),2))   Between 75 and 99  Then Convert(int ,OTHour)+1 Else  Convert(int ,OTHour) End End ,
OTTHour=Case When Convert(int,right(Convert(varchar(20),Convert(Decimal(18,2),OTTHour)),2))  Between 25 and 74  Then Convert(int ,OTTHour)+0.5  Else Case When  Convert(int,right(Convert(varchar(20),Convert(Decimal(18,2),OTTHour)),2))   Between 75 and 99  Then Convert(int ,OTTHour)+1 Else  Convert(int ,OTTHour) End End 

--分天匯總
--首先把有記錄的員工工號添加進來
Insert into #ALL(EmpCode) Select Distinct EmpCOde From #Tmp
--做天循環

Declare @FFDate Char(10)
Set @FFDate=Convert(Char(10),@FDate,111)

Declare @SQL Varchar(4000)
Declare @i int
Set @i=0
While @i<=DateDiff(Day,@FDate,@EDate)
	Begin
Set @SQL=' UpDate #All Set 
				ZBHour'+Convert(Varchar,@i+1)+'=B.ZBHour  ,
				OTHour'+Convert(Varchar,@i+1)+'=B.OTHour ,
				OTTHour'+Convert(Varchar,@i+1)+'=B.OTTHour ,
				ZBHour=A.ZBHour+B.ZBHour,
				OTHour=A.OTHour+B.OTHour,
				OTTHour=A.OTTHour+B.OTTHour
				From #All A Inner Join #Tmp B On A.EmpCode=B.EmpCode WHere B.FDate='''+@FFDate+''''
		Exec (@SQL)
		Delete #Tmp Where FDate=@FFDate
		Set @FFDate=Convert(Char(10),DateAdd(Day,@i+1,@FDate),111)
		Set @i=@i+1
	End

Update #ALL Set QTime=B.QTime,QTimeSalary=B.QTimeSalary from #ALL A Inner Join
(Select EmpCode, QTime=Sum(QTime)/60.0, QTimeSalary=Sum(QTimeSalary)/60.0 From PWERP_MS.dbo.RSKQResult Where FDate Between @FDate And @EDate Group By EmpCode) B On A.EmpCode=B.EmpCode


Insert into VN_YEAR_MONTH_ATTENDANCE(YEAR,MONTH,EMPLOYEENO,EMPLOYEE,DEPARTMENT,UNIT,
DAY1,
DAY2,
DAY3,
DAY4,
DAY5,
DAY6,
DAY7,
DAY8,
DAY9,
DAY10,
DAY11,
DAY12,
DAY13,
DAY14,
DAY15,
DAY16,
DAY17,
DAY18,
DAY19,
DAY20,
DAY21,
DAY22,
DAY23,
DAY24,
DAY25,
DAY26,
DAY27,
DAY28,
DAY29,
DAY30,
DAY31,
NOTE
) 
 Select '<year/>','<Month/>',B.RsEmpCode,B.CBName+' '+B.VName,VE.DEPARTMENT_ID,VE.UNIT_ID,
ZBHour1+OTHour1+OTTHour1,
ZBHour2+OTHour2+OTTHour2,
ZBHour3+OTHour3+OTTHour3,
ZBHour4+OTHour4+OTTHour4,
ZBHour5+OTHour5+OTTHour5,
ZBHour6+OTHour6+OTTHour6,
ZBHour7+OTHour7+OTTHour7,
ZBHour8+OTHour8+OTTHour8,
ZBHour9+OTHour9+OTTHour9,
ZBHour10+OTHour10+OTTHour10,
ZBHour11+OTHour11+OTTHour11,
ZBHour12+OTHour12+OTTHour12,
ZBHour13+OTHour13+OTTHour13,
ZBHour14+OTHour14+OTTHour14,
ZBHour15+OTHour15+OTTHour15,
ZBHour16+OTHour16+OTTHour16,
ZBHour17+OTHour17+OTTHour17,
ZBHour18+OTHour18+OTTHour18,
ZBHour19+OTHour19+OTTHour19,
ZBHour20+OTHour20+OTTHour20,
ZBHour21+OTHour21+OTTHour21,
ZBHour22+OTHour22+OTTHour22,
ZBHour23+OTHour23+OTTHour23,
ZBHour24+OTHour24+OTTHour24,
ZBHour25+OTHour25+OTTHour25,
ZBHour26+OTHour26+OTTHour26,
ZBHour27+OTHour27+OTTHour27,
ZBHour28+OTHour28+OTTHour28,
ZBHour29+OTHour29+OTTHour29,
ZBHour30+OTHour30+OTTHour30,
ZBHour31+OTHour31+OTTHour31,
''
From #All A Inner Join PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
Inner Join hr.dbo.VN_EMPLOYEE VE On B.RsEmpCode=VE.EMPLOYEENO 
Left Join PWERP_MS.dbo.RsBasDept C On C.Code=B.DeptCode 
where VE.DEPARTMENT_ID='<DEPARTMENT_ID/>'
and VE.UNIT_ID='<UNIT_ID/>'
Order By B.DeptCode,B.RsEmpCode


declare @DAY1 nvarchar(500),@DAY2 nvarchar(500),@DAY3 nvarchar(500),@DAY4 nvarchar(500),@DAY5 nvarchar(500),@DAY6 nvarchar(500),@DAY7 nvarchar(500)
		,@DAY8  nvarchar(500),@DAY9  nvarchar(500),@DAY10  nvarchar(500),@DAY11  nvarchar(500),@DAY12 nvarchar(500),@DAY13 nvarchar(500),@DAY14 nvarchar(500)
		,@DAY15  nvarchar(500),@DAY16  nvarchar(500),@DAY17  nvarchar(500),@DAY18  nvarchar(500),@DAY19  nvarchar(500),@DAY20 nvarchar(500),@DAY21 nvarchar(500)
		,@DAY22  nvarchar(500),@DAY23  nvarchar(500),@DAY24  nvarchar(500),@DAY25  nvarchar(500),@DAY26  nvarchar(500),@DAY27 nvarchar(500),@DAY28 nvarchar(500)
		,@DAY29  nvarchar(500),@DAY30  nvarchar(500),@DAY31  nvarchar(500),@EMPLOYEENO nvarchar(50),@EMPLOYEE  nvarchar(50),@lateCount int
		,@nDAY1  nvarchar(500),@nDAY2  nvarchar(500),@nDAY3  nvarchar(500),@nDAY4  nvarchar(500),@nDAY5  nvarchar(500),@nDAY6  nvarchar(500),@nDAY7  nvarchar(500)
		,@nDAY8  nvarchar(500),@nDAY9  nvarchar(500),@nDAY10  nvarchar(500),@nDAY11  nvarchar(500),@nDAY12  nvarchar(500),@nDAY13  nvarchar(500),@nDAY14  nvarchar(500)
		,@nDAY15  nvarchar(500),@nDAY16  nvarchar(500),@nDAY17  nvarchar(500),@nDAY18  nvarchar(500),@nDAY19  nvarchar(500),@nDAY20  nvarchar(500),@nDAY21  nvarchar(500)
		,@nDAY22  nvarchar(500),@nDAY23  nvarchar(500),@nDAY24  nvarchar(500),@nDAY25  nvarchar(500),@nDAY26  nvarchar(500),@nDAY27  nvarchar(500),@nDAY28  nvarchar(500)
		,@nDAY29  nvarchar(500),@nDAY30  nvarchar(500),@nDAY31  nvarchar(500),@YEAR nvarchar(50),@MONTH nvarchar(50),@hcode   nvarchar(500)

	
declare Select_late cursor for
        select YEAR ,MONTH,VY.EMPLOYEENO,VY.EMPLOYEE,DAY1,DAY2,DAY3,DAY4,DAY5,DAY6,DAY7,DAY8,DAY9,DAY10,DAY11,DAY12,DAY13,DAY14,DAY15
        ,DAY16,DAY17,DAY18,DAY19,DAY20,DAY21,DAY22,DAY23,DAY24,DAY25,DAY26,DAY27,DAY28,DAY29,DAY30,DAY31 from hr.dbo.VN_YEAR_MONTH_ATTENDANCE as VY
        Inner Join hr.dbo.VN_EMPLOYEE VE On VY.EMPLOYEENO=VE.EMPLOYEENO 
        WHERE YEAR='<year/>' and MONTH='<Month/>' and VE.DEPARTMENT_ID='<DEPARTMENT_ID/>' and VE.UNIT_ID='<UNIT_ID/>' 

open Select_late
fetch next from Select_late into @YEAR,@MONTH,@EMPLOYEENO,@EMPLOYEE,@DAY1,@DAY2,@DAY3,@DAY4,@DAY5,@DAY6,@DAY7,@DAY8,@DAY9,@DAY10,@DAY11,@DAY12,@DAY13,@DAY14,@DAY15
        ,@DAY16,@DAY17,@DAY18,@DAY19,@DAY20,@DAY21,@DAY22,@DAY23,@DAY24,@DAY25,@DAY26,@DAY27,@DAY28,@DAY29,@DAY30,@DAY31  --提取操作的列数据放到局部变量中

 Set @lateCount=0

   while @@fetch_status=0      --返回被 FETCH 语句执行的最后游标的状态
	  begin
	 
		 --比較當天班表應上班時數 小於應上班時數 查詢有無請假
		  if(convert(decimal(18, 2),@DAY1)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/01',@EMPLOYEENO)) )
	    begin 
		
			 Set @nDAY1=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'01')
			
		END
		else   
		begin
			Set @nDAY1=''
		END
				
	  if(convert(decimal(18, 2),@DAY2)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/02',@EMPLOYEENO)) )
	    begin 
			
			 Set @nDAY2=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'02')
		END
		ELSE
		begin
			Set @nDAY2=''
		END
	  if(convert(decimal(18, 2),@DAY3)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/03',@EMPLOYEENO)) )
	    begin 
	    	
			Set @nDAY3=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'03')
		
		END
		ELSE
		begin
			Set @nDAY3=''
		END
		
	  if(convert(decimal(18, 2),@DAY4)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/04',@EMPLOYEENO)) )
	    begin 
			Set @nDAY4=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'04')
		END
		ELSE
		begin
			Set @nDAY4=''
		END
	  if(convert(decimal(18, 2),@DAY5)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/05',@EMPLOYEENO)) )
	    begin 
			Set @nDAY5=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'05')
			
		END
		ELSE
		begin
			Set @nDAY5=''
		END
		
	  if(convert(decimal(18, 2),@DAY6)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/06',@EMPLOYEENO)) )
	    begin 
			set @nDAY6=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'06')	
		END
		ELSE
		begin
			Set @nDAY6=''
		END
	  if(convert(decimal(18, 2),@DAY7)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/07',@EMPLOYEENO)) )
	    begin 
			set @nDAY7=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'07')	
			
		END
		ELSE
		begin
			Set @nDAY7=''
		END
	  if(convert(decimal(18, 2),@DAY8)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/08',@EMPLOYEENO)) )
	    begin 
			set @nDAY8=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'08')	
		END
		ELSE
		begin
			Set @nDAY8=''
		END
	  if(convert(decimal(18, 2),@DAY9)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/09',@EMPLOYEENO)) )
	    begin 
			set @nDAY9=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'09')	
		END
		ELSE
		begin
			Set @nDAY9=''
		END
	 if(convert(decimal(18, 2),@DAY10)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/10',@EMPLOYEENO)) )
	    begin 
			set @nDAY10=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'10')	
		END
		ELSE
		begin
			Set @nDAY10=''
		END
		
	
		
	 if(convert(decimal(18, 2),@DAY11)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/11',@EMPLOYEENO)) )
	    begin 
			set @nDAY11=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'11')	
		END
		ELSE
		begin
			Set @nDAY11=''
		END
	 	 if(convert(decimal(18, 2),@DAY12)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/12',@EMPLOYEENO)) )
	    begin 
			set @nDAY12=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'12')
		END
		ELSE
		begin
			Set @nDAY12=''
		END
	  if(convert(decimal(18, 2),@DAY13)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/13',@EMPLOYEENO)) )
	    begin 
			set @nDAY13=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'13')
		END
		ELSE
		begin
			Set @nDAY13=''
		END
	 if(convert(decimal(18, 2),@DAY14)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/14',@EMPLOYEENO)) )
	    begin 
			set @nDAY14=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'14')
		END
		ELSE
		begin
			Set @nDAY14=''
		END
	   if(convert(decimal(18, 2),@DAY15)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/15',@EMPLOYEENO)) )
	    begin 
			Set @nDAY15=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'15')
		END
		ELSE
		begin
			Set @nDAY15=''
		END
	if(convert(decimal(18, 2),@DAY16)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/16',@EMPLOYEENO)) )
	    begin 
			Set @nDAY16=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'16')
		END
		ELSE
		begin
			Set @nDAY16=''
		END
	if(convert(decimal(18, 2),@DAY17)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/17',@EMPLOYEENO)) )
	    begin 
			Set @nDAY17=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'17')
		END
		ELSE
		begin
			Set @nDAY17=''
		END
	 	if(convert(decimal(18, 2),@DAY18)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/18',@EMPLOYEENO)) )
	    begin 
			Set @nDAY18=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'18')
		END
		ELSE
		begin
			Set @nDAY18=''
		END
	  if(convert(decimal(18, 2),@DAY19)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/19',@EMPLOYEENO)) )
	    begin 
			Set @nDAY19=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'19')
		END
		ELSE
		begin
			Set @nDAY19=''
		END
		
	   if(convert(decimal(18, 2),@DAY19)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/20',@EMPLOYEENO)) )
	    begin 
			Set @nDAY20=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'20')
		END
		ELSE
		begin
			Set @nDAY20=''
		END
		
	   if(convert(decimal(18, 2),@DAY19)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/21',@EMPLOYEENO)) )
	    begin 
			Set @nDAY21=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'21')
		END
		ELSE
		begin
			Set @nDAY21=''
		END
		
	   if(convert(decimal(18, 2),@DAY19)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/22',@EMPLOYEENO)) )
	    begin 
			Set @nDAY22=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'22')
		END
		ELSE
		begin
			Set @nDAY22=''
		END
		
	    if(convert(decimal(18, 2),@DAY19)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/23',@EMPLOYEENO)) )
	    begin 
			Set @nDAY23=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'23')
		END
		ELSE
		begin
			Set @nDAY23=''
		END
		
	    if(convert(decimal(18, 2),@DAY19)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/24',@EMPLOYEENO)) )
	    begin 
			Set @nDAY24=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'24')
		END
		ELSE
		begin
			Set @nDAY24=''
		END
		
	    if(convert(decimal(18, 2),@DAY19)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/25',@EMPLOYEENO)) )
	    begin 
			Set @nDAY25=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'25')
		END
		ELSE
		begin
			Set @nDAY25=''
		END
		
	    if(convert(decimal(18, 2),@DAY19)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/26',@EMPLOYEENO)) )
	    begin 
			Set @nDAY26=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'26')
		END
		ELSE
		begin
			Set @nDAY26=''
		END
		
	    if(convert(decimal(18, 2),@DAY19)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/27',@EMPLOYEENO)) )
	    begin 
			Set @nDAY27=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'27')
		END
		ELSE
		begin
			Set @nDAY27=''
		END
		
	   if(convert(decimal(18, 2),@DAY19)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/28',@EMPLOYEENO)) )
	    begin 
			Set @nDAY28=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'28')
		END
		ELSE
		begin
			Set @nDAY28=''
		END
		
	   if(convert(decimal(18, 2),@DAY19)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/29',@EMPLOYEENO)) )
	    begin 
			Set @nDAY29=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'29')
		END
		ELSE
		begin
			Set @nDAY29=''
		END
		
	    if(convert(decimal(18, 2),@DAY19)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/30',@EMPLOYEENO)) )
	    begin 
			Set @nDAY30=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'30')
		END
		ELSE
		begin
			Set @nDAY30=''
		END
		
	    if(convert(decimal(18, 2),@DAY19)<convert(decimal(18, 2),hr.dbo.Func_TurnHour('<year/>/<Month/>/31',@EMPLOYEENO)) )
	    begin 
			Set @nDAY31=hr.dbo.Func_Month_Attendance(@EMPLOYEENO,@YEAR,@MONTH,'31')
		END
		ELSE
		begin
			Set @nDAY31=''
		END
		
	    Set @SQL=' update VN_YEAR_MONTH_ATTENDANCE '
	    +'  set DAY1='''+@nDAY1+''', DAY2='''+@nDAY2+''', DAY3='''+@nDAY3+''' , DAY4='''+@nDAY4+''' , DAY5='''+@nDAY5+''' , DAY6='''+@nDAY6+''''
	    +'  ,DAY7='''+@nDAY7+''', DAY8='''+@nDAY8+''', DAY9='''+@nDAY9+''' , DAY10='''+@nDAY10+''' , DAY11='''+@nDAY11+''' , DAY12='''+@nDAY12+''''
	    +'  ,DAY13='''+@nDAY13+''', DAY14='''+@nDAY14+''', DAY15='''+@nDAY15+''' , DAY16='''+@nDAY16+''' , DAY17='''+@nDAY17+''' , DAY18='''+@nDAY18+''''
	    +'  ,DAY19='''+@nDAY19+''', DAY20='''+@nDAY20+''', DAY21='''+@nDAY21+''' , DAY22='''+@nDAY22+''' , DAY23='''+@nDAY23+''' , DAY24='''+@nDAY24+''''
	    +'  ,DAY25='''+@nDAY25+''', DAY26='''+@nDAY26+''', DAY27='''+@nDAY27+''' , DAY28='''+@nDAY28+''' , DAY29='''+@nDAY29+''' , DAY30='''+@nDAY30+''''
	    +'  ,DAY31='''+@nDAY31+''' '
	    +' where EMPLOYEENO='''+@EMPLOYEENO+''' and YEAR='+@YEAR+' and MONTH='+@MONTH+''
	  
	    Insert Into #SAL(SSQL ,lateCount)VALUES(@SQL,@lateCount)
	    SET @lateCount=@lateCount+1
	   Exec (@SQL)
      fetch next from Select_late into  @YEAR,@MONTH,@EMPLOYEENO,@EMPLOYEE,@DAY1,@DAY2,@DAY3,@DAY4,@DAY5,@DAY6,@DAY7,@DAY8,@DAY9,@DAY10,@DAY11,@DAY12,@DAY13,@DAY14,@DAY15
        ,@DAY16,@DAY17,@DAY18,@DAY19,@DAY20,@DAY21,@DAY22,@DAY23,@DAY24,@DAY25,@DAY26,@DAY27,@DAY28,@DAY29,@DAY30,@DAY31  --提取操作的列数据放到局部变量中
	  end
close Select_late      
deallocate Select_late


--select * from  #Tmp
--select * from  #ALL
--select * from  PWERP_MS.dbo.Tmp_RSKQMonthDay
--select * from  #SAL
Drop Table #Tmp
Drop Table #ALL
Drop Table #SAL