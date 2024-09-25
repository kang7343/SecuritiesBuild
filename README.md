# 自律研修プロジェクト *Securities.Build();* 📈

## 📆 作業期間
2024.09.02 ~ 2023.09.27 (3週間)

## 💡 プロジェクトの背景
- 
- 

## 🚩 システムのターゲット
- 株式投資・情報の見方を知りたい初心者
- 証券関連の英単語をより楽しく勉強したい学習者
- 国内株式の情報を収集したい外国人

## 🌟 システムの特徴
- オープンAPIを用いた最新データとの連携
- 証券SEという職務の特殊性を反映したUIデザイン

## 💻 機能一覧
- 全国内上場会社のリアルタイムデータの推移照会（*20分遅れ）
- 証券関連英単語の学習
- 市場/33業種区分による銘柄の条件検索  
- お気に入り銘柄のブックマークリスト管理

## 🔧 開発環境

### Front-End
<div align=left> 
  <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> 
  <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> 
  <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> 
  <img src="https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white">
  
</div>

### Back-End <img src="https://img.shields.io/badge/Made%20for-VSCode-1f425f.svg">
<div align=left> 
  <img src="https://img.shields.io/badge/Spring DATA JPA-1572B6?style=for-the-badge&logo=Spring Boot&logoColor=white">
  <img src="https://img.shields.io/badge/Spring Security-E34F26?style=for-the-badge&logo=Spring Boot&logoColor=white"> 
  <img src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white"> 
  <br>
  <img src="https://img.shields.io/badge/GRADLE-02303A?style=for-the-badge&logo=GRADLE&logoColor=white"> 
  <img src="https://img.shields.io/badge/LOMBOK-E10098?style=for-the-badge&logo&logoColor=white">
  <img src="https://img.shields.io/badge/sequelize-323330?style=for-the-badge&logo=sequelize&logoColor=blue">
  <img src="https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white">
   <img src="https://img.shields.io/badge/MyBatis-000000?style=for-the-badge&logo=MyBatis&logoColor=white"> 

### Education
 <img src="https://img.shields.io/badge/ChatGPT-74aa9c?style=for-the-badge&logo=openai&logoColor=white">
</div>
<br>

## 📑 プロジェクト成果物

<details>
<summary>フォルダ構成</summary>
<br />
  
```
src
 └─ main
     ├─ java
     │   └─ com
     │       └─ securitiesbuild
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
         │   └─ securitiesbuild
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

#### 1️⃣ Main 
#### 2⃣ Data 
#### 3⃣ Vocabulary  
#### 4⃣ Screener
#### 5⃣ Bookmarks
#### 6⃣ Details


</details>

<details>
<summary>ERD</summary>
</details>

<details>
<summary>アーキテクチャ</summary>
</details>

## 🔎 画面遷移
<details>
<summary>URL一覧</summary>
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
