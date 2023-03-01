**Invoices Test Task**

In this project I used MVVM architecture, because it’s faster to implement and good enough for so
small project. Covered InvoiceRepositoryImpl with Unit-tests.

I used dagger-hilt for dependency injection.
I used library from github to create zigzag effects on invoices.
For requests, I used retrofit and custom call adapter for handling errors.
For recycler view, I used custom fingerprintAdapter (Adapter Delegations) to work with recyclerview efficiently.
For testing, I used mockk to mock classes and coroutines-test for replacing coroutines dispatcher.

You can run apk in the root project folder or set up a project and run on your machine.