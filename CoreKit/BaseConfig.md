- 圆角 RCConner : `radius` 优先级最低，其他有值以其他为准

```json
{
  "radius": 10,
  "leftTop": 10,
  "rightTop": 10,
  "rightBottom": 10,
  "leftBottom": 10
}
```

- 缩进 RCInsets : 左，上，右，下的 margin 或 padding

```json
{
  "left": 10,
  "top": 10,
  "right": 10,
  "bottom": 10
}
```

- 颜色 RCColor : 颜色值 rgb(0~255), a(0~1)

```json
{
  "red": 255,
  "green": 255,
  "blue": 255,
  "alpha": 0.8
}
```

- 图标 RCImage : `local` 本地图片；`remote` 远端图片，优先展示

```json
{
  "local": "rckit_ic_settings.png",
  "remote": "https://test.png"
}
```

- 颜色选择器 RCColorSelector : `normal` 正常颜色，`select` 选中的颜色

```json
{
  "normal": "RCColor",
  "select": "RCColor"
}
```

- 图片选择器 RCImageSelector : `normal` 正常图片，`select` 选中的图片

```json
{
  "normal": "RCImage",
  "select": "RCImage"
}
```

- 大小 RCSize : 控件的宽高, -1：占全宽或全高，-2：包裹内容

```json
{
  "width": 100,
  "height": 100
}
```

- 文字，按钮，输入框等相关 RCAttribute

```json
{
  "textColor": "RCColor",
  "textSize": 14,
  "text": "测试",
  "hintColor": "RCColor",
  "hintText": "聊一聊吧",
  "background": "RCColor",
  "corner": "RCCorner",
  "imageSelector": "RCImageSelector",
  "colorSelector": "RCColorSelector",
  "size": "RCSize",
  "insets": "RCInsets",
  "image": "RCImage"
}
```

