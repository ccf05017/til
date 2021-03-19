use study_db;

# Mysql Full Outer Join 예시
# Mysql은 기본 문법으로 Full Outer Join을 지원하지 않기 때문에 이런 방법을 사용해야 한다.
select e.ename, e.job, e.sal, d.loc
from EMP e left join DEPT d on e.deptno = d.deptno
union
select e.ename, e.job, e.sal, d.loc
from EMP e right join DEPT d on e.deptno = d.deptno;

# union 키워드로 쿼리를 위 아래로 결합하기
# union vs union all => union all은 중복이 제거되지 않은 상태로 쿼리를 처리한다.
(select deptno, sum(sal)
from emp
group by deptno)
union
select cast('0' as unsigned) as deptno, sum(sal)
from emp;

# intersect를 통해 교집합을 나타내기
# 하지만 mysql에서 지원되지 않기 때문에 join으로 처리해야 한다.
# 예시는 아래 사이트에서 참고
# http://intomysql.blogspot.com/2011/01/mysql-minus-intersect.html

# 서브쿼리
# 단일행 서브쿼리로 특정 조건을 만족하는 한개의 대상을 조건으로 사용할 수 있다.
select ename, sal
from emp
where sal > (select sal
	from emp
    where ename = 'JONES');

# 다중행 서브쿼리로 특정 조건을 만족하는 집합을 조건으로 사용할 수 있다.
select ename, sal
from emp
where sal in (select sal
	from emp
    where job='SALESMAN');
