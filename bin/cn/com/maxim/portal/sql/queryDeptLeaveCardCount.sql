SELECT COUNT( ID) AS COUNT
FROM VN_DEPT_LEAVE_ROLE
  where DEPT='<DEPT/>'
  AND UNIT='<UNIT/>'
  AND STATUS='<STATUS/>'
  AND ROLE='<ROLE/>'