-- =====================================================
-- SETUP: Create table and insert test data
-- =====================================================

DROP TABLE IF EXISTS StudentEnrollments;

CREATE TABLE StudentEnrollments (
    student_id INT PRIMARY KEY,
    student_name VARCHAR(100) NOT NULL,
    course_id VARCHAR(10) NOT NULL,
    enrollment_date DATE NOT NULL
);

INSERT INTO StudentEnrollments VALUES
(1, 'Ashish', 'CSE101', '2024-06-01'),
(2, 'Smaran', 'CSE102', '2024-06-01'),
(3, 'Vaibhav', 'CSE103', '2024-06-01');

SELECT * FROM StudentEnrollments;

-- =====================================================
-- PART A: DEADLOCK SIMULATION
-- (Run these in TWO SEPARATE SESSIONS)
-- =====================================================

-- SESSION 1 (User A)
START TRANSACTION;
UPDATE StudentEnrollments 
SET enrollment_date = '2024-07-01' 
WHERE student_id = 1;
SELECT 'Session 1: Locked student_id = 1' AS status;

-- Run this after Session 2 locks student_id=2
UPDATE StudentEnrollments 
SET enrollment_date = '2024-07-02' 
WHERE student_id = 2;
SELECT 'Session 1: Trying to lock student_id = 2' AS status;
COMMIT;

-- SESSION 2 (User B)
START TRANSACTION;
UPDATE StudentEnrollments 
SET enrollment_date = '2024-08-01' 
WHERE student_id = 2;
SELECT 'Session 2: Locked student_id = 2' AS status;

-- Run this after Session 1 locks student_id=1
UPDATE StudentEnrollments 
SET enrollment_date = '2024-08-02' 
WHERE student_id = 1;
SELECT 'Session 2: Trying to lock student_id = 1' AS status;
COMMIT;

-- Expected: One session gets a deadlock error

-- =====================================================
-- PART B: MVCC DEMONSTRATION
-- =====================================================

-- Reset data
UPDATE StudentEnrollments 
SET enrollment_date = '2024-06-01' 
WHERE student_id = 1;

-- SESSION 1 (User A - Reader)
SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;
START TRANSACTION;

-- First read
SELECT student_id, student_name, course_id, enrollment_date, 
       'Initial read by User A' AS action
FROM StudentEnrollments 
WHERE student_id = 1;

-- (Pause here, let Session 2 update before continuing)

-- Second read (should see old value due to MVCC)
SELECT student_id, student_name, course_id, enrollment_date, 
       'Second read by User A (after User B update)' AS action
FROM StudentEnrollments 
WHERE student_id = 1;

COMMIT;

-- Read after commit (should now see new value)
SELECT student_id, student_name, course_id, enrollment_date, 
       'Read by User A after commit' AS action
FROM StudentEnrollments 
WHERE student_id = 1;

-- SESSION 2 (User B - Writer)
START TRANSACTION;
UPDATE StudentEnrollments 
SET enrollment_date = '2024-07-10' 
WHERE student_id = 1;

SELECT student_id, student_name, course_id, enrollment_date, 
       'Updated by User B' AS action
FROM StudentEnrollments 
WHERE student_id = 1;
COMMIT;

-- =====================================================
-- PART C: COMPARISON WITH AND WITHOUT MVCC
-- =====================================================

-- Reset data
UPDATE StudentEnrollments 
SET enrollment_date = '2024-06-01' 
WHERE student_id = 1;

-- SCENARIO 1: With locking (Non-MVCC)
-- SESSION 1
START TRANSACTION;
SELECT student_id, student_name, course_id, enrollment_date, 
       'User A: Locked read' AS action
FROM StudentEnrollments 
WHERE student_id = 1
FOR UPDATE;

-- Keep open, Session 2 will be blocked until commit
SELECT 'User A: Holding lock...' AS status;
COMMIT;

-- SESSION 2
START TRANSACTION;
SELECT 'User B: Attempting update (may be blocked)' AS status;
UPDATE StudentEnrollments 
SET enrollment_date = '2024-07-15' 
WHERE student_id = 1;
COMMIT;

-- SCENARIO 2: MVCC (Non-blocking reads)
-- Reset data
UPDATE StudentEnrollments 
SET enrollment_date = '2024-06-01' 
WHERE student_id = 1;

-- SESSION 1
SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;
START TRANSACTION;
SELECT student_id, student_name, course_id, enrollment_date, 
       'User A: Non-locking read (MVCC)' AS action
FROM StudentEnrollments 
WHERE student_id = 1;

-- Wait while Session 2 updates
SELECT 'User A: Still sees old snapshot' AS status;

SELECT student_id, student_name, course_id, enrollment_date, 
       'User A: Second read (consistent snapshot)' AS action
FROM StudentEnrollments 
WHERE student_id = 1;
COMMIT;

-- SESSION 2
START TRANSACTION;
UPDATE StudentEnrollments 
SET enrollment_date = '2024-08-20' 
WHERE student_id = 1;

SELECT student_id, student_name, course_id, enrollment_date, 
       'User B: Update completed' AS action
FROM StudentEnrollments 
WHERE student_id = 1;
COMMIT;


SELECT * FROM StudentEnrollments ORDER BY student_id;

-- Show current transaction isolation level
SELECT @@transaction_isolation;

-- Extra info (safe to run)
SHOW VARIABLES LIKE 'transaction_isolation';
SHOW VARIABLES LIKE 'innodb_lock_wait_timeout';
