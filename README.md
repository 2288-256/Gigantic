Unofficial Gigantic Plugin
======
Unofficial Gigantic Plugin (仮称) は、seichi.click network で公開されていた [整地鯖(春)](https://www.seichi.network/spring) のSpigotプラグインをGPL-3.0ライセンスの条件下でフォークした非公式プラグインです。<br /><br />
整地鯖(春)で使用されていたプラグインのソースコードは[こちら](https://github.com/GiganticMinecraft/Gigantic) (Public archive)<br /><br />
整地鯖(春)との変更点は[こちら](https://github.com/2288-256/Unofficial-Gigantic/releases)

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

## Commit and PR Style
PRのタイトルとコミットメッセージは[Conventional Commits](https://www.conventionalcommits.org/ja/v1.0.0/)の形式に従ってください。

## Contributing Guide

貢献方法については[こちら](https://github.com/2288-256/Unofficial-Gigantic/wiki/Contributing-Guide-(%E8%B2%A2%E7%8C%AE%E6%96%B9%E6%B3%95))か[Discord](https://discord.gg/6ccJNEP5G4)の[チャンネル](https://discord.com/channels/1316682322606559305/1316700763954876470)をご覧ください

## Branch Model
[Git-flow](https://qiita.com/KosukeSone/items/514dd24828b485c69a05)を簡略化したものを使用します。<br>
新規に機能を開発する際はdevelopブランチからfeatureブランチを作り、そこで作業してください。<br />
開発が終了したらdevelopブランチにマージします。<br>
masterブランチは本番環境に反映されます。<br />
本番環境を更新するタイミングでdevelopブランチをmasterブランチにマージします。
