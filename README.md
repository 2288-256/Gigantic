Unofficial Gigantic Plugin
======
Unofficial Gigantic Plugin (仮称) は、seichi.click network で公開されていた [整地鯖(春)](https://www.seichi.network/spring) のSpigotプラグインをGPL-3.0ライセンスの条件下でフォークした非公式プラグインです。<br /><br />
整地鯖(春)で使用されていたプラグインのソースコードは[こちら](https://github.com/GiganticMinecraft/Gigantic) (Public archive)<br /><br />
整地鯖(春)との変更点はこちら (制作中)

Description
---
当プラグインはMySQLを用いて全データベースを管理しています．
デバッグサーバ起動時には”gigantic”データベースを作成済であることを確認してください．


Development
---
* [IntelliJ IDEA 2019.1.3](https://www.jetbrains.com/idea/)
* [java 1.8](http://www.oracle.com/technetwork/java/javase/overview/index.html)
* [spigot 1.14.2-R0.1-SNAPSHOT](https://www.spigotmc.org/)

Requirement
---

* [ProtocolLib 4.4.0-SNAPSHOT](https://www.spigotmc.org/resources/protocollib.1997/)


## Kotlin Style Guide
基本的には[スマートテック・ベンチャーズ Kotlinコーディング規約](https://github.com/SmartTechVentures/kotlin-style-guide)に準拠します。

## Nullable
!!演算子は，原則使用禁止とするが，Nullではないことが明確な場合は使用可能とする．


## JavaDocs
publicなメソッドについては、JavaDocsを記載するよう心がけてください。
その他は各自が必要だと判断した場合のみ記載してください。

## Commit Style
1コミットあたりの情報は最小限としてください。<br />
コミットメッセージは英語の動詞から始めることを推奨しています。

## Branch Model
[Git-flow](https://qiita.com/KosukeSone/items/514dd24828b485c69a05)を簡略化したものを使用します。<br>
新規に機能を開発する際はdevelopブランチからfeatureブランチを作り、そこで作業してください。<br />
開発が終了したらdevelopブランチにマージします。<br>
masterブランチは本番環境に反映されます。<br />
本番環境を更新するタイミングでdevelopブランチをmasterブランチにマージします。
