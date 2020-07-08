const customIterator = () => ({
    [Symbol.iterator]: () => ({
        x: 0,
        next() {
            if (this.x > 100) {
                return {
                    done: true,
                    value: this.x
                };
            }
            return {
                done: false,
                value: this.x++
            };
        }
    })
});
  

for (const value of customIterator()) {
    console.log(value);
}

const asyncCustomIterator = () => ({
    [Symbol.asyncIterator]: () => ({
        x: 0,

        next() {
            if(this.x > 100) {
                return Promise.resolve({
                    done: true,
                    value: this.x
                });
            }

            let y = this.x++;

            return Promise.resolve({
                done: false,
                value: y
            });
        }
    })
});

(async () => {
    for await (let x of asyncCustomIterator()) {
      console.log(x);
    }
})();