(() => {
  let currentYOffset = 0;   // 현재 y offset의 값
  let sumOfpreviousSceneY = 0;    // 이전 scene들의 y 높이의 합
  let currentScene = 0;   // 현재 보여줘야 할 scene 번호

  const sceneInfo = [
    {
      type: 'sticky',
      heightNum: 5,
      scrollHeight: 0,
      objs: {
        container: document.querySelector('#scroll-section-0')
      }
    },
    {
      type: 'normal',
      heightNum: 5,
      scrollHeight: 0,
      objs: {
        container: document.querySelector('#scroll-section-1')
      }
    },
    {
      type: 'sticky',
      heightNum: 5,
      scrollHeight: 0,
      objs: {
        container: document.querySelector('#scroll-section-2')
      }
    },
    {
      type: 'sticky',
      heightNum: 5,
      scrollHeight: 0,
      objs: {
        container: document.querySelector('#scroll-section-3')
      }
    }
  ];

  function setLayout() {
    sceneInfo.forEach((scene) => {
      scene.scrollHeight = scene.heightNum * window.innerHeight;
      scene.objs.container.style.height = `${scene.scrollHeight}px`;
    });
  }

  function scrollLoop() {
    sumOfpreviousSceneY = 0;
    for (let i = 0; i < currentScene; i++) {
      sumOfpreviousSceneY += sceneInfo[i].scrollHeight;
    }

    if (currentYOffset > sumOfpreviousSceneY + sceneInfo[currentScene].scrollHeight) {
      currentScene++;
    }

    if (currentYOffset < sumOfpreviousSceneY) {
      currentScene--;
    }

    console.log(currentScene);
  }

  window.addEventListener('resize', setLayout);
  window.addEventListener('scroll', () => {
    currentYOffset = window.pageYOffset;     // 스크롤이 움직일 때마다 현재의 y 좌표를 추적함.
    scrollLoop();
  });
  
  setLayout();
})();