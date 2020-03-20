* [ ] Step 1 : launch kubernetes
* [ ] Step 2 :
```sh
$ kubectl apply -k .
```

* [ ] Step 3 : 
--
# Linux
$ curl -Lo kubebox https://github.com/astefanutti/kubebox/releases/download/v0.7.0/kubebox-linux && chmod +x kubebox
# OSX
$ curl -Lo kubebox https://github.com/astefanutti/kubebox/releases/download/v0.7.0/kubebox-macos && chmod +x kubebox
# Windows
$ curl -Lo kubebox.exe https://github.com/astefanutti/kubebox/releases/download/v0.7.0/kubebox-windows.exe
--

Then run:
```sh
$ ./kubebox
```


* [ ] Step 4 :
create pods with commands :
```sh
$ kubectl create -f 'app\deployments-fusiion\authentification-deployment-fusiion.yaml'
$ kubectl create -f 'app\deployments-fusiion\client-deployment-fusiion.yaml'
$ kubectl create -f 'app\deployments-fusiion\collaborateurs-deployment-fusiion.yaml'
$ kubectl create -f 'app\deployments-fusiion\competences-deployment-fusiion.yaml'
```
OR 
```sh
$ kubectl create -f 'app\deployments-fusiion\'
```