(() => {
  let currentYOffset = 0;   // 현재 y offset의 값
  let sumOfpreviousSceneY = 0;    // 이전 scene들의 y 높이의 합
  let currentScene = 0;   // 현재 보여줘야 할 scene 번호
  let isNewScene = false; // 새 scene 진입여부 확인용

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
        canvas: document.querySelector('#video-canvas-0'),
        context: document.querySelector('#video-canvas-0').getContext('2d'),
        videoImages: [],
      },
      effects: {
        vidioImageCount: 300,
        imageSequence: [0, 299],
        canvasOpacity: [1, 0, { start: 0.9, end: 1}],

        messageAOpacityIn: [0, 1, { start: 0.1, end: 0.2 }],
				messageBOpacityIn: [0, 1, { start: 0.3, end: 0.4 }],
				messageCOpacityIn: [0, 1, { start: 0.5, end: 0.6 }],
        messageDOpacityIn: [0, 1, { start: 0.7, end: 0.8 }],
        
				messageATranslateYIn: [20, 0, { start: 0.1, end: 0.2 }],
				messageBTranslateYIn: [20, 0, { start: 0.3, end: 0.4 }],
				messageCTranslateYIn: [20, 0, { start: 0.5, end: 0.6 }],
        messageDTranslateYIn: [20, 0, { start: 0.7, end: 0.8 }],
        
				messageAOpacityOut: [1, 0, { start: 0.25, end: 0.3 }],
				messageBOpacityOut: [1, 0, { start: 0.45, end: 0.5 }],
				messageCOpacityOut: [1, 0, { start: 0.65, end: 0.7 }],
        messageDOpacityOut: [1, 0, { start: 0.85, end: 0.9 }],
        
				messageATranslateYOut: [0, -20, { start: 0.25, end: 0.3 }],
				messageBTranslateYOut: [0, -20, { start: 0.45, end: 0.5 }],
				messageCTranslateYOut: [0, -20, { start: 0.65, end: 0.7 }],
				messageDTranslateYOut: [0, -20, { start: 0.85, end: 0.9 }]
      },
    },
    {
      type: 'normal',
      // heightNum: 5,
      scrollHeight: 0,
      objs: {
        container: document.querySelector('#scroll-section-1')
      },
    },
    {
      type: 'sticky',
      heightNum: 5,
      scrollHeight: 0,
      objs: {
        container: document.querySelector('#scroll-section-2'),
				messageA: document.querySelector('#scroll-section-2 .a'),
				messageB: document.querySelector('#scroll-section-2 .b'),
				messageC: document.querySelector('#scroll-section-2 .c'),
				pinB: document.querySelector('#scroll-section-2 .b .pin'),
        pinC: document.querySelector('#scroll-section-2 .c .pin'),
        canvas: document.querySelector('#video-canvas-2'),
        context: document.querySelector('#video-canvas-2').getContext('2d'),
        videoImages: [],
      },
      effects: {
        vidioImageCount: 960,
        imageSequence: [0, 959],
        canvasOpacity: [1, 0, { start: 0.9, end: 1}],

				messageATranslateYIn: [20, 0, { start: 0.25, end: 0.3 }],
				messageBTranslateYIn: [30, 0, { start: 0.6, end: 0.65 }],
        messageCTranslateYIn: [30, 0, { start: 0.87, end: 0.92 }],
        
				messageAOpacityIn: [0, 1, { start: 0.25, end: 0.3 }],
				messageBOpacityIn: [0, 1, { start: 0.6, end: 0.65 }],
        messageCOpacityIn: [0, 1, { start: 0.87, end: 0.92 }],
        
				messageATranslateYOut: [0, -20, { start: 0.4, end: 0.45 }],
				messageBTranslateYOut: [0, -20, { start: 0.68, end: 0.73 }],
        messageCTranslateYOut: [0, -20, { start: 0.95, end: 1 }],
        
				messageAOpacityOut: [1, 0, { start: 0.4, end: 0.45 }],
				messageBOpacityOut: [1, 0, { start: 0.68, end: 0.73 }],
        messageCOpacityOut: [1, 0, { start: 0.95, end: 1 }],
        
				pinBScaleY: [0.5, 1, { start: 0.6, end: 0.65 }],
				pinCScaleY: [0.5, 1, { start: 0.87, end: 0.92 }]
			},
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

  function setCanvasImages() {
    let imgElem;
    const firstScene = sceneInfo[0];

    for(let i = 0; i < firstScene.effects.vidioImageCount; i++) {
      imgElem = new Image();
      imgElem.src = `video/001/IMG_${6726 + i}.jpg`;
      firstScene.objs.videoImages.push(imgElem);
    }

    let imgElem2;
    const thirdScene = sceneInfo[2];

    for(let i = 0; i < thirdScene.effects.vidioImageCount; i++) {
      imgElem2 = new Image();
      imgElem2.src = `video/002/IMG_${7027 + i}.jpg`;
      thirdScene.objs.videoImages.push(imgElem2)
    }
  }

  setCanvasImages();

  function setLayout() {
    sceneInfo.forEach((scene) => {
      if (scene.type === 'sticky') {
        scene.scrollHeight = scene.heightNum * window.innerHeight;
        scene.objs.container.style.height = `${scene.scrollHeight}px`;
      } else {
        scene.scrollHeight = scene.objs.container.offsetHeight;
        scene.objs.container.style.height = `${scene.scrollHeight}px`;
      }
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

    const heightRatio = window.innerHeight / 1080;
    sceneInfo[0].objs.canvas.style.transform = `translate3d(-50%, -50%, 0) scale(${heightRatio})`;
    sceneInfo[2].objs.canvas.style.transform = `translate3d(-50%, -50%, 0) scale(${heightRatio})`;
  }

  function getYOffsetOfCurrentScene() {
    return currentYOffset - sumOfpreviousSceneY;
  }

  function getScrollRatio() {
    const scrollHeight = sceneInfo[currentScene].scrollHeight;

    return getYOffsetOfCurrentScene() / scrollHeight;
  }

  function calculateEffects(effects) {
    const effectRange = effects[1] - effects[0];
    const scrollHeight = sceneInfo[currentScene].scrollHeight;
    const scrollRatio = getScrollRatio(effects);
    const currentYOffsetInScene = currentYOffset - sumOfpreviousSceneY;

    if (effects.length === 3) {
      // 애니메이션 진행시간이 존재하는 경우
      const { start, end } = effects[2];

      const partEffectStartPoint = start * scrollHeight;
      const partEffectEndPoint = end * scrollHeight;
      const partEffectRange = partEffectEndPoint - partEffectStartPoint;
      const currentEffectRatio = getYOffsetOfCurrentScene() - partEffectStartPoint;

      if (currentYOffsetInScene >= partEffectStartPoint && currentYOffsetInScene <= partEffectEndPoint) {
        return ((currentEffectRatio / partEffectRange)  * effectRange) + effects[0];
      } else if (currentYOffsetInScene < partEffectStartPoint) {
        return effects[0];
      } else if (currentYOffsetInScene > partEffectEndPoint) {
        return effects[1];
      }
      
    } else {
      // 애니메이션 진행시간이 존재하지 않는 경우
      return (scrollRatio * effectRange) + effects[0];
    }
  }

  function playFirstScene() {
    const { objs, effects } = sceneInfo[currentScene];

    let sequence = Math.round(calculateEffects(effects.imageSequence));
    objs.context.drawImage(objs.videoImages[sequence], 0, 0);
    objs.canvas.style.opacity = calculateEffects(effects.canvasOpacity);

    if (getScrollRatio() < 0.22) {
      objs.messageA.style.opacity = calculateEffects(effects.messageAOpacityIn);
      objs.messageA.style.transform = `translate3D(0, ${calculateEffects(effects.messageATranslateYIn)}%, 0)`;
    } else {
      objs.messageA.style.opacity = calculateEffects(effects.messageAOpacityOut)
      objs.messageA.style.transform = `translate3D(0, ${calculateEffects(effects.messageATranslateYOut)}%, 0)`;
    }

    if (getScrollRatio() < 0.42) {
      objs.messageB.style.opacity = calculateEffects(effects.messageBOpacityIn);
      objs.messageB.style.transform = `translate3D(0, ${calculateEffects(effects.messageBTranslateYIn)}%, 0)`;
    } else {
      objs.messageB.style.opacity = calculateEffects(effects.messageBOpacityOut)
      objs.messageB.style.transform = `translateY(${calculateEffects(effects.messageBTranslateYOut)}%)`;
    }

    if (getScrollRatio() < 0.62) {
      objs.messageC.style.opacity = calculateEffects(effects.messageCOpacityIn);
      objs.messageC.style.transform = `translateY(${calculateEffects(effects.messageCTranslateYIn)}%)`;
    } else {
      objs.messageC.style.opacity = calculateEffects(effects.messageBOpacityOut)
      objs.messageC.style.transform = `translateY(${calculateEffects(effects.messageCTranslateYOut)}%)`;
    }

    if (getScrollRatio() < 0.82) {
      objs.messageD.style.opacity = calculateEffects(effects.messageDOpacityIn);
      objs.messageD.style.transform = `translate3D(0, ${calculateEffects(effects.messageDTranslateYIn)}%, 0)`;
    } else {
      objs.messageD.style.opacity = calculateEffects(effects.messageBOpacityOut)
      objs.messageD.style.transform = `translate3d(0, ${calculateEffects(effects.messageDTranslateYOut)}%, 0)`;
    }
  }

  function playThirdScene() {
    const { objs, effects } = sceneInfo[currentScene];

    let sequence = Math.round(calculateEffects(effects.imageSequence));
    objs.context.drawImage(objs.videoImages[sequence], 0, 0);
    objs.canvas.style.opacity = calculateEffects(effects.canvasOpacity);

    if (getScrollRatio() < 0.32) {
      objs.messageA.style.opacity = calculateEffects(effects.messageAOpacityIn);
      objs.messageA.style.transform = `translate3d(0, ${calculateEffects(effects.messageATranslateYIn)}%, 0)`;
    } else {
      objs.messageA.style.opacity = calculateEffects(effects.messageAOpacityOut);
      objs.messageA.style.transform = `translate3d(0, ${calculateEffects(effects.messageATranslateYOut)}%, 0)`;
    }

    if (getScrollRatio() < 0.67) {
      objs.messageB.style.opacity = calculateEffects(effects.messageBOpacityIn);
      objs.messageB.style.transform = `translate3d(0, ${calculateEffects(effects.messageBTranslateYIn)}%, 0)`;
      objs.pinB.style.transform = `scaleY(${calculateEffects(effects.pinBScaleY)})`;
    } else {
      objs.messageB.style.opacity = calculateEffects(effects.messageBOpacityOut);
      objs.messageB.style.transform = `translate3d(0, ${calculateEffects(effects.messageBTranslateYIn)}%, 0)`;
      objs.pinB.style.transform = `scaleY(${calculateEffects(effects.pinBScaleY)})`;
    }

    if (getScrollRatio() < 0.93) {
      objs.messageC.style.opacity = calculateEffects(effects.messageCOpacityIn);
      objs.messageC.style.transform = `translate3d(0, ${calculateEffects(effects.messageCTranslateYIn)}%, 0)`;
      objs.pinC.style.transform = `scaleY(${calculateEffects(effects.pinCScaleY)})`;
    } else {
      objs.messageC.style.opacity = calculateEffects(effects.messageCOpacityOut);
      objs.messageC.style.transform = `translate3d(0, ${calculateEffects(effects.messageCTranslateYIn)}%, 0)`;
      objs.pinC.style.transform = `scaleY(${calculateEffects(effects.pinCScaleY)})`;
    }
  }

  function playFourthScene() {
    console.log('scene 4');
  }

  function playAnimation() {
    if (currentScene === 0) {
      playFirstScene();
    }
    if (currentScene === 2) {
      playThirdScene();
    }
    if (currentScene === 3) {
      playFourthScene();
    }
  }

  function scrollLoop() {
    isNewScene = false;
    sumOfpreviousSceneY = 0;

    for (let i = 0; i < currentScene; i++) {
      sumOfpreviousSceneY += sceneInfo[i].scrollHeight;
    }

    if (currentYOffset > sumOfpreviousSceneY + sceneInfo[currentScene].scrollHeight) {
      isNewScene = true;
      currentScene++;
      document.body.setAttribute('id', `show-scene-${currentScene}`);
    }

    if (currentYOffset < sumOfpreviousSceneY) {
      isNewScene = true;
      if (currentScene == 0) return   // 브라우저에 따라 바운스 효과 발생 시 scroll이 음수로 가는 경우 방지
      currentScene--;
      document.body.setAttribute('id', `show-scene-${currentScene}`);
    }

    if (isNewScene) return;   // scene 바뀔 때 발생할 수 있는 음수를 제거

    playAnimation();
  }

  window.addEventListener('resize', setLayout);
  window.addEventListener('scroll', () => {
    currentYOffset = window.pageYOffset;     // 스크롤이 움직일 때마다 현재의 y 좌표를 추적함.
    scrollLoop();
  });
  window.addEventListener('load', () => {
    setLayout();
    sceneInfo[0].objs.context.drawImage(sceneInfo[0].objs.videoImages[0], 0, 0);
  });
  window.addEventListener('resize', setLayout);
})();