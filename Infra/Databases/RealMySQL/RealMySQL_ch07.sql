use employees;

# Mysql 인덱스 통계 정보 확인 쿼리
show index from departments;
show index from dept_emp;
show index from dept_manager;
show index from employee_name;
show index from employees;
show index from salaries;
show index from tb_dual;
show index from titles;

# index_subquery 실행 계획 확인
# in 쿼리의 결과로 중복이 존재할 수 있지만, 인덱스로 제거할 수 있을 때 사용된다.
explain
select * from departments where dept_no in (
select dept_no from dept_emp where dept_no between 'd001' and 'd003');

# range scan 실행 계획 확인
explain
select dept_no from dept_emp where dept_no between 'd001' and 'd003';

# index_merge 실행 계획
explain 
select * from employees
where emp_no between 10001 and 11000
	or first_name='Smith';
