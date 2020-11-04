alter table users
add (level int);

alter table users
add (login int);

alter table users
add (recommend int);

alter table users
alter level int not null;

alter table users
alter login int not null;

alter table users
alter recommend int not null;
