const rl = require('readline').createInterface({
    input: process.stdin,
    output: process.stdout
  });
  
  let emps = [];
  
  function menu() {
    console.log('\nemployee management system');
    console.log('1. add employee');
    console.log('2. list employees');
    console.log('3. remove employee');
    console.log('4. exit');
  
    rl.question('\nenter your choice: ', c => {
      switch (c.trim()) {
        case '1':
          add();
          break;
        case '2':
          list();
          break;
        case '3':
          remove();
          break;
        case '4':
          rl.close();
          break;
        default:
          console.log('invalid choice.');
          menu();
      }
    });
  }
  
  function add() {
    rl.question('enter employee name: ', n => {
      rl.question('enter employee id: ', i => {
        emps.push({ n, i });
        console.log(`employee ${n} (id: ${i}) added successfully.`);
        menu();
      });
    });
  }
  
  function list() {
    console.log('\nemployee list:');
    emps.forEach((e, idx) => {
      console.log(`${idx + 1}. name: ${e.n}, id: ${e.i}`);
    });
    menu();
  }
  
  function remove() {
    rl.question('enter employee id to remove: ', i => {
      const idx = emps.findIndex(e => e.i === i.trim());
      if (idx !== -1) {
        const emp = emps.splice(idx, 1)[0];
        console.log(`employee ${emp.n} (id: ${emp.i}) removed successfully.`);
      } else {
        console.log('employee not found.');
      }
      menu();
    });
  }
  
  menu();
  