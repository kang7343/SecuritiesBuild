# 📈 株式情報取得システム *Securities.Build();* 

## 目次
- [概要](#概要)
- [データの収集](#データの収集)
- [プロジェクト設計](#プロジェクト設計)
- [システム詳細](#システム詳細)
  
## 概要 

### 作業期間
- 2024.09.02 ~ 2024.09.27 (3週間)

### プロジェクトの背景
- 自分のような未経験者が、株式の知識に気軽に接するアプリケーションを作りたかった
- 普段は触れない金融英語の基礎を学ぶきっかけを得たかった

### システムのターゲット
- 株式投資・情報の見方を知りたい初心者
- 証券関連の英単語をより楽しく勉強したい学習者
- 国内株式の情報を収集したい外国人

### システムの特徴
- 金融機関のオープンAPIを用いた最新データとの連携
- 証券SEという職務の特殊性を反映したUIデザイン

### 機能一覧
- 全国内上場会社のリアルタイムデータの推移照会（*20分遅れ）
- 証券関連英単語の学習
- 市場/33業種区分による銘柄の条件検索  
- お気に入り銘柄のブックマークリスト管理

### 開発環境 <img src="https://img.shields.io/badge/Made%20for-VSCode-1f425f.svg">

#### Front-End
<div align=left> 
  <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> 
  <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> 
  <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> 
  <img src="https://img.shields.io/badge/chart.js-F5788D.svg?style=for-the-badge&logo=chart.js&logoColor=white">
  <img src="https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white">
  
</div>

#### Back-End 
<div align=left> 
  <img src="https://img.shields.io/badge/Spring Boot-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white">
  <img src="https://img.shields.io/badge/Spring WebFlux-1572B6?style=for-the-badge&logo=Spring Boot&logoColor=white">
  <img src="https://img.shields.io/badge/Spring Security-E34F26?style=for-the-badge&logo=Spring Boot&logoColor=white"> 
  <img src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white"> 
  <br>
  <img src="https://img.shields.io/badge/GRADLE-02303A?style=for-the-badge&logo=GRADLE&logoColor=white"> 
  <img src="https://img.shields.io/badge/LOMBOK-E10098?style=for-the-badge&logo&logoColor=white">
  <img src="https://img.shields.io/badge/sequelize-323330?style=for-the-badge&logo=sequelize&logoColor=blue">
  <img src="https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white">
   <img src="https://img.shields.io/badge/MyBatis-000000?style=for-the-badge&logo=MyBatis&logoColor=white"> 

#### Education
 <img src="https://img.shields.io/badge/ChatGPT-74aa9c?style=for-the-badge&logo=openai&logoColor=white">
</div>

#### Git Conventional Commit Messages
- `feat` 機能の追加
- `fix` バグの修正
- `docs` ドキュメントの変更
- `style` コードのフォーマット変更（空白、セミコロンなど）
- `refactor` コードのリファクタリング
- `perf` パフォーマンスの改善
- `test` テストの追加 or 修正
- `chore` その他の些細な変更
<br />

## データの収集
### J-Quants API
- https://jpx.gitbook.io/j-quants-ja/api-reference/listed_info
- 2024.06.18 時点で上場している銘柄の一覧をPostmanを利用してjsonファイルで取得
  
![image](https://github.com/user-attachments/assets/cc0c1a02-a358-4267-af90-5679ab631c32)

### データの格納
- 取得したjsonファイルからnode.jsとsequelizeを利用して必要なデータのみを抽出し、ローカルDB(stock)に保存
- modelの呼び出し ➡ processedDataの読み込み ➡ dataをjsonオブジェクトに変換 ➡ DBに銘柄一覧をinsert
  
```
util
 ├─ .env // 環境変数設定。今回はPostgresのPW情報を格納
 ├─ config.js
 │   // DB接続の設定を管理するためのオブジェクトを作成。modelを定義するためにsequelizeインスタンスを生成
 │　 // DBのテーブルとマッピングするmodelを定義できる
 ├─ dataProcessor.js // rawDataを加工して格納したいカラム-値のみを抽出してjsonファイルを作成
 ├─ index.js // .envを読み込むためのdotenvパッケージを入れている
 ├─ insertData.js // テーブルにデータを挿入するためのロジック
 ├─ model.js // カラムのタイプなどテーブルの構造を定義。queryを実行し、データを操作できる
 ├─ processedData.json // dataProcessorを実行した結果のデータ
 └─ rawData.json // Postmanで取得したJ-Quantsの初期データ。加工の対象
```

model.js

![image](https://github.com/user-attachments/assets/7e72e194-6673-4a1d-b638-2d08df2ac132)

insertData.js

![image](https://github.com/user-attachments/assets/d75b577d-3df6-42a2-b35e-e9c62a42e269)

- powershellで実行してクエリが正常終了すると、console()のメッセージが表示される。
- テーブルが存在しない場合、新規作成されるようにsequelize.sync({ force:false })メソッドを使用
  
  - { force:false }：テーブルが存在する場合、新規作成しない
  - { force:true }：既存のテーブルを削除し、新規作成

テーブルが存在しない場合に実行

![image](https://github.com/user-attachments/assets/7f3f03aa-e516-4ef8-80e4-d9cac3419b9c)

テーブルが存在する場合に実行

![image](https://github.com/user-attachments/assets/ba42a1f0-311c-4649-8d51-c4b4241232b4)

正常終了。selectで格納できたことを確認

![image](https://github.com/user-attachments/assets/d97a9510-46bf-4606-853c-ca3242014ed0)


### KIS Developers（韓国投資証券）Open API
- https://apiportal.koreainvestment.com/apiservice/apiservice-oversea-stock-quotations#L_3eeac674-072d-4674-a5a7-f0ed01194a81
- REST方式を採用している韓国の金融機関の外国株式データ照会APIを利用。Spring WebfluxのWebClientインタフェースを通じて非同期でHTTPコンテンツを取得するロジックを実装した
- パラメータとして4桁の銘柄コードが求められるが、J-Quantsから取得したデータは予備コード「0」を含めた５桁だったので、SQLのRTRIM関数で4桁に変換してから利用した
- ex. 銘柄詳細情報照会機能のコントローラ
  
```java
@GetMapping("/equities-tse/{id}")
    public Mono<String> getCurrentPrice(@PathVariable("id") String id, Model model) {
        // getSearchInfoとgetCurrentPriceTseはどちらも非同期処理を返すので、Monoのzipメソッドを使って2つのMonoを結合
        Mono<Body> searchInfoMono = kisService.getSearchInfo(id); // 詳細情報
        Mono<Body> currentPriceTseMono = kisService.getCurrentPriceTse(id); // 現在株価
        Mono<Body> currentPriceDetailMono = kisService.getCurrentPriceDetail(id); // 現在株価詳細

        // 日単位
        List<String> iscdsAndOtherVariable2 = Arrays.asList(id);
        Flux<IndexData> dailyPriceFlux = Flux.fromIterable(iscdsAndOtherVariable2)
                .concatMap(tuple -> kisService.getDailyPrice(id))
                .map(jsonData -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        return objectMapper.readValue(jsonData, IndexData.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });

        List<IndexData> dailyPriceList = dailyPriceFlux.collectList().block();

        // 分単位
        List<String> iscdsAndOtherVariable3 = Arrays.asList(id);
        Flux<IndexData> minutePriceFlux = Flux.fromIterable(iscdsAndOtherVariable3)
                .concatMap(tuple -> kisService.getMinutePrice(id))
                .map(jsonData -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        return objectMapper.readValue(jsonData, IndexData.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });

        List<IndexData> minutePriceList = minutePriceFlux.collectList().block();

        return Mono.zip(searchInfoMono, currentPriceTseMono, currentPriceDetailMono)
                .doOnNext(tuple -> {
                    // タプルで返された2つのBodyオブジェクトをモデルに追加
                    model.addAttribute("info", tuple.getT1().getOutput());
                    model.addAttribute("equity", tuple.getT2().getOutput());
                    model.addAttribute("detail", tuple.getT3().getOutput());
                    model.addAttribute("jobDate", kisService.getJobDateTime());
                    model.addAttribute("daily", dailyPriceList);
                    model.addAttribute("minute", minutePriceList);
                })
                .thenReturn("equities-tse");
    }
```
- ex. thymeleaf + javascriptで銘柄一覧のテーブルの銘柄コードをAPIのパラメータとして渡し、データを呼び出せるようにした
  
```java
<th:block th:each="stock : ${stocks}">
  <tr class="clickable-row" th:data-symbol="${stock.code}">

... // 銘柄一覧のテーブル

<script>
  document.addEventListener("DOMContentLoaded", function () {
    var rows = document.querySelectorAll('.clickable-row');
      rows.forEach(function (row) {
        row.addEventListener('click', function () {
          var symbol = row.getAttribute('data-symbol');
            if (symbol) {
              window.location.href = '/equities-tse/' + encodeURIComponent(symbol);
                } else {
                  console.error('No symbol found for this row.');
                }
              });
            });
         });
</script>
```
  
## プロジェクト設計

<details>
<summary>フォルダ構成</summary>
<br />
  
```
src
 └─ main
     ├─ java
     │   └─ com
     │       └─ sb
     │           ├─ controller
     │           │   ├─ BookmarkController.java
     │           │   ├─ GlobalControllerAdvice.java
     │           │   ├─ KisController.java
     │           │   ├─ LoginController.java
     │           │   ├─ MainController.java
     │           │   ├─ ScreenerController.java
     │           │   ├─ UserController.java
     │           │   └─ VocabularyController.java
     │           ├─ entity
     │           │   ├─ BookmarkWithStockInfo.java
     │           │   ├─ Market.java
     │           │   ├─ Sector.java
     │           │   ├─ Stock.java
     │           │   ├─ StockBookmark.java
     │           │   ├─ StockSearchCriteria.java
     │           │   ├─ User.java
     │           │   └─ Vocabulary.java
     │           ├─ form
     │           │   └─ UserForm.java
     │           ├─ helper
     │           │   └─ UserHelper.java
     │           ├─ model
     │           │   ├─ Body.java
     │           │   ├─ IndexData.java
     │           │   ├─ OauthInfo.java
     │           │   └─ TokenInfo.java
     │           ├─ repository
     │           │   ├─ KisRepository.java
     │           │   ├─ MarketRepository.java
     │           │   ├─ SectorRepository.java
     │           │   ├─ StockBookmarkRepository.java
     │           │   ├─ StockRepository.java
     │           │   ├─ UserRepository.java
     │           │   └─ VocabularyRepository.java
     │           ├─ security
     │           │   ├─ AccessTokenManager.java
     │           │   ├─ KisConfig.java
     │           │   ├─ SecurityConfig.java
     │           │   ├─ UserAccountDetails.java
     │           │   └─ UserAccountDetailsService.java
     │           ├─ service
     │           │   ├─ KisService.java
     │           │   ├─ MarketService.java
     │           │   ├─ MarketServiceImpl.java
     │           │   ├─ SectorService.java
     │           │   ├─ SectorServiceImpl.java
     │           │   ├─ StockBookmarkService.java
     │           │   ├─ StockBookmarkServiceImpl.java
     │           │   ├─ StockService.java
     │           │   ├─ StockServiceImpl.java
     │           │   ├─ UserService.java
     │           │   ├─ UserServiceImpl.java
     │           │   ├─ VocabularyService.java
     │           │   └─ VocabularyServiceImpl.java
     │           └─ Application.java
     └─ resources
         ├─ com
         │   └─ sb
         │       └─ repository
         │           ├─ MarketRepository.xml
         │           ├─ SectorRepository.xml
         │           ├─ StockBookmarkRepository.xml
         │           ├─ StockRepository.xml
         │           ├─ UserRepository.xml
         │           └─ VocabularyRepository.xml
         ├─ static
         ├─ templates
         │   ├─ user
         │   │   ├─ complete.html
         │   │   ├─ confirm.html
         │   │   ├─ login.html
         │   │   └─ register.html
         │   ├─ bookmarks.html
         │   ├─ equities-tse.html
         │   ├─ index.html
         │   ├─ indices.html
         │   ├─ main-adv.html
         │   ├─ main-amount.html
         │   ├─ main-price.html
         │   ├─ main.html
         │   ├─ nav.html
         │   ├─ screener.html
         │   └─ vocabulary.html
         └─ application.properties

 ```
 </details>

<details>
<summary>ワイヤーフレーム</summary>
<br />

</details>

<details>
<summary>ERD</summary>

![ERD](https://github.com/user-attachments/assets/78d5d99c-e737-4011-8170-0329675b9a54)

</details>

<details>
<summary>アーキテクチャ</summary>

![UseCase](https://github.com/user-attachments/assets/85d4789e-e790-4aed-82d3-1d0c45256c99)

</details>


## システム詳細

<details>
<summary>URL一覧</summary>
  
<br />
  
画面名|コントローラー|メソッド|ビュー
---|---|---|---
[メイン（銘柄ランキング一覧）画面](http://localhost:8080/main)|MainController|menu|login/menu.html
[ログイン画面](http://localhost:8080/user/login)|LoginController|login|user/login.html
[ユーザーアカウント登録（入力）画面](http://localhost:8080/user/account/register)|UserController|register|user/register.html
[ユーザーアカウント登録（確認）画面](http://localhost:8080/admin/account/confirm)|UserController|confirm|user/confirm.html
[ユーザーアカウント登録（完了）画面](http://localhost:8080/admin/account/complete)|UserController|complete|user/complete.html
[主要指数一覧画面](http://localhost:8080/indicies)|KisController|majorIndicies|indicies.html
[銘柄詳細情報画面](http://localhost:8080//equities-tse/{id})|KisController|getCurrentPrice|equities-tse.html
[英単語学習画面](http://localhost:8080/voca)|VocabularyController|getVocabularies|vocabulary.html
[銘柄スクリーナー画面](http://localhost:8080/screener)|ScreenerController|stockScreener|screener.html
[ブックマーク一覧画面](http://localhost:8080/mypage)|BookmarkController|getBookmarks|bookmarks.html
[エラー画面](http://localhost:8080/error)|ErrorController|error|error.html
</details>

<details>
<summary>デモンストレーション</summary>
<br />
  
1. ログイン
2. アカウント登録
3. 株式ランキング一覧
4. 主要指数照会
5. 用語学習
6. 銘柄スクリーニング
7. ブックマーク
8. 銘柄詳細照会

</details>
