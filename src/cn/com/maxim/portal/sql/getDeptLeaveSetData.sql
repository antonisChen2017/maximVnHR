SELECT D.DEPARTMENT as '部門'
      ,(case 
        when 
        U.UNIT is null 
        then '无'
        else 
        U.UNIT end) AS '單位'
        ,(case 
        when 
        L.[GROUP] is null OR L.[GROUP]='0'
        then '无'
        else 
        L.[GROUP] end) AS '組別'
      ,R.TITLE AS '员工身份'
      ,(case 
        when 
        STATUS ='1' 
        then '三天以上'
        else 
        '三天以下' end) AS '請假流程'
	   ,(case 
        when 
        SINGROLEL1 ='1' OR  SINGROLEL2 ='1' OR  SINGROLEL3 ='1' OR  SINGROLEL4 ='1'
        then '已设定'
        else 
        '未设定' end) AS '狀態'
   FROM VN_DEPT_LEAVE_ROLE L
LEFT JOIN VN_DEPARTMENT D
ON D.ID = L.DEPT
LEFT JOIN VN_UNIT U
ON U.ID = L.UNIT
LEFT JOIN VN_ROLE R
ON R.ID = L.ROLE