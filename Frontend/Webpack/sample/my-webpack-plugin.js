class MyWebapckPlugin {
  apply(compiler) {
    compiler.hooks.done.tap('My Plugin', stats => {
      console.log('MyPlugin: done');
    });
  }
}

module.exports = MyWebapckPlugin;
