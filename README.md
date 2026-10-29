# Lab 01 — Invoice Refactoring

## الفريق: 1724595329

| الاسم الكامل             | الرقم الجامعي | GitHub Username                                   | الدور                             |
| ------------------------ | ------------- | ------------------------------------------------- | --------------------------------- |
| هبة الله محمد سمير سنوبر | 1724595329    | [Heba23Snoubar](https://github.com/Heba23Snoubar) | Lead , والتسليم                   |
| عامر ابراهيم برهوق       | 1700004070    | [amerbarhouk](https://github.com/amerbarhouk)    | النسخة القديمة                    |
| خديجة بشار حسون          | 1722197130    | [**khadijheh**](https://github.com/khadijheh)     | UML,LCOM1                         |
| هبة عبد الكريم عباس      | 1722204693    | [Heba-Abbas](https://github.com/Heba-Abbas)                                        | البيانات والحساب والتنسيق والخدمة |
| أحمد خيران خالد          | 1722204095    | [ahmad-khaled-dev](https://github.com/ahmad-khaled-dev)                                  | التخزين والإرسال والتجميع         |

## التشغيل

### النسخة القديمة

تشغيل `s1.lab1.Main` بوصفه Java Application.

### النسخة الجديدة

تشغيل `s1.lab2.Main` بوصفه Java Application.
## تحليل النسخة القديمة (`s1.lab1`)

### المسؤوليات وأسباب التغيير

| المسؤولية | الدوال | سبب التغيير |
| --- | --- | --- |
| الحساب | `calculateTotal()` | تغيّر نسبة الخصم أو حد الخصم أو نسبة الضريبة |
| الطباعة والتنسيق | `printInvoice()` | تغيّر شكل نص الفاتورة |
| التخزين | `saveToDatabase(String)` | تغيّر وجهة الحفظ أو صيغة السطر المحفوظ |
| الإرسال | `sendEmail()` و `sendwhatsapp()` | تغيّر قناة الإرسال أو صيغة الرسالة |
| البيانات | الحقول `number` و `customerEmail` و `items` و `addItem()` | تغيّر بنية الفاتورة |

الصنف `Invoice` القديم يملك خمسة أسباب مستقلة للتغيير، وهذا مخالفة لمبدأ المسؤولية الواحدة (SRP).

### حساب LCOM1 للصنف القديم `Invoice`

**القواعد المعتمدة:**
- LCOM1 = عدد أزواج الدوال التي لا تشترك في أي حقل كائن.
- لا يُحسب الـ constructor ولا دوال الوصول (getters) ولا الحقول `static`.
- نعتمد **الاستخدام المباشر فقط** للحقول (استدعاء دالة لدالة أخرى لا يُعدّ استخدامًا للحقول).

**حقول الكائن:** `number`, `customerEmail`, `items`
(`DATABASE` و `FILES` حقلان static فلا يُحسبان).

**الدوال المحسوبة (6):** `addItem`, `calculateTotal`, `printInvoice`, `saveToDatabase`, `sendEmail`, `sendwhatsapp`
(`getItems` مستثناة لأنها getter). عدد الأزواج = 6 × 5 ÷ 2 = 15.

**الحقول التي تستخدمها كل دالة مباشرة:**

| الدالة | الحقول |
| --- | --- |
| `addItem` | items |
| `calculateTotal` | items |
| `printInvoice` | number, items |
| `saveToDatabase` | number, customerEmail |
| `sendEmail` | customerEmail, number |
| `sendwhatsapp` | number |

**جدول الأزواج (15 زوجًا):**

| # | الزوج | حقل مشترك | يُحسب في LCOM1؟ |
| --- | --- | --- | --- |
| 1 | addItem – calculateTotal | items | لا |
| 2 | addItem – printInvoice | items | لا |
| 3 | addItem – saveToDatabase | — | **نعم** |
| 4 | addItem – sendEmail | — | **نعم** |
| 5 | addItem – sendwhatsapp | — | **نعم** |
| 6 | calculateTotal – printInvoice | items | لا |
| 7 | calculateTotal – saveToDatabase | — | **نعم** |
| 8 | calculateTotal – sendEmail | — | **نعم** |
| 9 | calculateTotal – sendwhatsapp | — | **نعم** |
| 10 | printInvoice – saveToDatabase | number | لا |
| 11 | printInvoice – sendEmail | number | لا |
| 12 | printInvoice – sendwhatsapp | number | لا |
| 13 | saveToDatabase – sendEmail | customerEmail, number | لا |
| 14 | saveToDatabase – sendwhatsapp | number | لا |
| 15 | sendEmail – sendwhatsapp | number | لا |

**النتيجة: LCOM1 (Invoice القديم) = 6**
## التصميم الجديد

- UML قبل إعادة الهيكلة: `docs/uml-before.png`
- UML بعد إعادة الهيكلة: `docs/uml-after.png`

## LCOM1: قبل وبعد

**القاعدة المعتمدة:** عدد أزواج الدوال التي لا تشترك في أي حقل كائن، مع استخدام مباشر للحقول فقط. لا يُحسب الـ constructor ولا الـ getters ولا الحقول static. الصنف بلا حقول كائن يُكتب له "لا ينطبق".

| Class | Before | After |
| --- | --- | --- |
| Invoice | 6 | 0 |
| InvoiceItem | 0 | 0 |
| InvoiceCalculator | — | لا ينطبق (بلا حقول كائن) |
| InvoiceFormatter | — | لا ينطبق (بلا حقول كائن) |
| InvoiceStorage | — | لا ينطبق (واجهة) |
| DatabaseStorage | — | لا ينطبق (الحقل static فقط) |
| FileStorage | — | لا ينطبق (الحقل static فقط) |
| InvoiceSender | — | لا ينطبق (واجهة) |
| EmailSender | — | لا ينطبق (بلا حقول كائن) |
| WhatsAppSender | — | لا ينطبق (بلا حقول كائن) |
| InvoiceService | — | 3 |
| Main | — | لا ينطبق (بلا حقول كائن) |

### الصنفان `Invoice` و `InvoiceItem` بعد التعديل

- `Invoice`: الدالة المحسوبة الوحيدة `addItem` (الباقي getters)، فلا أزواج، والقيمة 0.
- `InvoiceItem`: constructor وgetters فقط، فلا دوال محسوبة، والقيمة 0.

### حساب `InvoiceService`

حقول الكائن: `calculator`, `formatter`, `storage`, `sender`. الدوال المحسوبة: 4، فعدد الأزواج 6.

| الدالة | الحقول المستخدمة مباشرة |
| --- | --- |
| `calculateTotal` | calculator |
| `printInvoice` | calculator, formatter |
| `save` | calculator, formatter, storage |
| `send` | sender |

(`send` تستدعي `printInvoice` لكن الاستدعاء غير المباشر لا يُحسب.)

| # | الزوج | حقل مشترك | يُحسب؟ |
| --- | --- | --- | --- |
| 1 | calculateTotal – printInvoice | calculator | لا |
| 2 | calculateTotal – save | calculator | لا |
| 3 | calculateTotal – send | — | **نعم** |
| 4 | printInvoice – save | calculator, formatter | لا |
| 5 | printInvoice – send | — | **نعم** |
| 6 | save – send | — | **نعم** |

**LCOM1 (InvoiceService) = 3**

### المقارنة قبل وبعد

| المقياس | قبل | بعد |
| --- | --- | --- |
| LCOM1 للصنف الرئيسي | 6 (`Invoice`) | 0 (`Invoice`) |
| أعلى LCOM1 بين الأصناف | 6 | 3 (`InvoiceService`) |
| مسؤوليات الصنف الرئيسي | 5 (بيانات، حساب، طباعة، تخزين، إرسال) | 1 لكل صنف |
| عدد الأصناف | 2 (`Invoice`, `InvoiceItem`) + `Main` | 12 (منها واجهتان) |

**ملاحظة:** `InvoiceService` قيمته 3 لا 0، لأن كل دالة فيه تستخدم اعتماديات مختلفة، وهذا متوقع في صنف يُنسّق الخطوات. التحسن الأساسي أن الحساب والتنسيق والتخزين والإرسال صارت بأصناف منفصلة، وأن `Invoice` لم يعد يحمل أياً منها.
## التحقق

ملف التحقق: `docs/verification.pdf`

يتضمن الملف صورًا لمخرجات النسختين القديمة والجديدة للمقارنة والتأكد من تطابق السلوك.
