Promise.resolve("done")
  .then(val => {
    throw new Error("fail");
  })
  .then(val => console.log(val))
  .catch(err => console.log(err))
  .finally(_ => console.log("** this part must excute **"));

Promise.resolve("done")
  .then(val => {
    return "this is success case"
  })
  .then(val => console.log(val))
  .catch(err => console.log(err))
  .finally(_ => console.log("** this part must excute **"));