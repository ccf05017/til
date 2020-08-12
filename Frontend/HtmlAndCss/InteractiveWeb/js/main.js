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
        container: document.querySelector('#scroll-section-0'),
        messageA: document.querySelector('#scroll-section-0 .main-message.a'),
        messageB: document.querySelector('#scroll-section-0 .main-message.b'),
        messageC: document.querySelector('#scroll-section-0 .main-message.c'),
        messageD: document.querySelector('#scroll-section-0 .main-message.d'),
      },
      effects: {
        messageAOpacity: [0, 1],
      },
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

    const currentYOffset = window.pageYOffset;

    let totalScrollHeight = 0;
    for (let i = 0; i < sceneInfo.length; i++) {
      totalScrollHeight += sceneInfo[i].scrollHeight;
      if (totalScrollHeight >= currentYOffset) {
        currentScene = i;
        break;
      }
    }
    document.body.setAttribute('id', `show-scene-${currentScene}`);
  }

  function getYOffsetOfCurrentScene() {
    return currentYOffset - sumOfpreviousSceneY;
  }

  function calculateEffects(effects) {
    const effectRange = effects[1] - effects[0];
    const scrollRatio = getYOffsetOfCurrentScene() / sceneInfo[currentScene].scrollHeight;

    return (effectRange * scrollRatio) + effects[0];
  }

  function playFirstScene() {
    const { objs, effects } = sceneInfo[currentScene];

    const messageAOpacityIn = calculateEffects(effects.messageAOpacity);
    objs.messageA.style.opacity = messageAOpacityIn;
  }

  function playSecondScene() {
    console.log('scene 2');
  }

  function playThirdScene() {
    console.log('scene 3');
  }

  function playFourthScene() {
    console.log('scene 4');
  }

  function playAnimation() {
    if (currentScene === 0) {
      playFirstScene();
    }
    if (currentScene === 1) {
      playSecondScene();
    }
    if (currentScene === 2) {
      playThirdScene();
    }
    if (currentScene === 3) {
      playFourthScene();
    }
  }

  function scrollLoop() {
    sumOfpreviousSceneY = 0;
    for (let i = 0; i < currentScene; i++) {
      sumOfpreviousSceneY += sceneInfo[i].scrollHeight;
    }

    if (currentYOffset > sumOfpreviousSceneY + sceneInfo[currentScene].scrollHeight) {
      currentScene++;
      document.body.setAttribute('id', `show-scene-${currentScene}`);
    }

    if (currentYOffset < sumOfpreviousSceneY) {
      if (currentScene == 0) return   // 브라우저에 따라 바운스 효과 발생 시 scroll이 음수로 가는 경우 방지
      currentScene--;
      document.body.setAttribute('id', `show-scene-${currentScene}`);
    }

    playAnimation();
  }

  window.addEventListener('resize', setLayout);
  window.addEventListener('scroll', () => {
    currentYOffset = window.pageYOffset;     // 스크롤이 움직일 때마다 현재의 y 좌표를 추적함.
    scrollLoop();
  });
  window.addEventListener('load', setLayout);
  window.addEventListener('resize', setLayout);
})();