---
title: Memuat Naik Log
description: Cara memuat naik log untuk menyelesaikan masalah.
authors:
  - IMB11

search: false
---

# Memuat Naik Log

Apabila menyelesaikan masalah, anda selalunya perlu menyediakan log untuk membantu mengenal pasti punca isu tersebut.

## Mengapa saya perlu memuat naik log?

Memuat naik log membolehkan orang lain membantu anda dalam menyelesaikan masalah anda dengan lebih cepat daripada hanya menampal log ke dalam ruang perbualan atau siaran forum. Ia juga membolehkan anda berkongsi log anda dengan orang lain tanpa perlu menyalin dan menampalnya.

Sesetengah tapak penampalan juga menyediakan penyerlahan sintaks untuk log, yang menjadikannya lebih mudah dibaca dan mungkin menapis maklumat sensitif, seperti nama pengguna anda atau maklumat sistem.

## Laporan Ranap Permainan

Laporan ranap permainan dijana secara automatik apabila permainan ranap. Ia hanya mengandungi maklumat ranap dan bukan log sebenar permainan. Ia terletak di dalam folder `crash-reports` dalam direktori permainan.

Untuk mendapatkan maklumat lanjut tentang laporan ranap permainan, lihat [Laporan Ranap Permainan](./crash-reports).

## Mencari Lokasi Log

Panduan ini merangkumi Pelancar Minecraft rasmi (biasanya dirujuk sebagai "pelancar vanila") - untuk pelancar pihak ketiga, anda harus merujuk dokumentasi mereka.

Log-log terletak di dalam folder `log` dalam direktori permainan, direktori permainan boleh didapati di lokasi berikut bergantung pada sistem pengoperasian anda:

::: code-group

```:no-line-numbers [Windows]
%appdata%\.minecraft
```

```:no-line-numbers [macOS]
~/Library/Application Support/minecraft
```

```:no-line-numbers [Linux]
~/.minecraft
```

:::

Log terkini dipanggil `latest.log`, dan log sebelumnya menggunakan corak penamaan `yyyy-mm-dd_number.log.gz`.

## Memuat Naik Log

Log boleh dimuat naik ke pelbagai perkhidmatan, seperti:

- [Pastebin](https://pastebin.com/)
- [GitHub Gist](https://gist.github.com/)
- [mclo.gs](https://mclo.gs/)
