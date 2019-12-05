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