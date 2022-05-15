# Javaによるオセロ実装．

## オセロの遊び方
8×8の正方形の盤と、表裏を黒と白に塗り分けた平たい円盤状の石を使用する。それぞれ黒と白を担当する2人のプレイヤーが交互に盤面へ石を打っていき、最終的に盤上の石が多かったほうが勝ちとなる。相手の石を自分の石で挟んだときは、相手の石を裏返すことで、自分の石にする。
(Wikipedia参照)

- スタート時<br>
![image](https://user-images.githubusercontent.com/78514639/168420251-725db69e-694d-4b3c-9855-150747fc3766.png)

- 途中画面<br>
赤いマスがプレイヤーの設置マスを表し，置きたい場所を矢印キーを用いて指定し，Enterキーで石を設置する．
青いマスはComputerの設置マスを表している．<br>
![image](https://user-images.githubusercontent.com/78514639/168464027-a8ed8819-e488-4803-aa57-ca90bfad820a.png)

- 結果画面<br>
Escキーを押すことで，ゲームを終了させることができる．<br>
![image](https://user-images.githubusercontent.com/78514639/168464262-f558f3e4-ba90-4248-8a5e-4705f55cd586.png)