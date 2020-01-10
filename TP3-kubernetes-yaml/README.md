* [ ] Step 1 : launch kubernetes
* [ ] Step 2 :
```sh
$ ./run.sh
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
$ kubectl create -f 'app-fusiion\Deployments-fusiion\authentification-deployment-fusiion.yaml'
$ kubectl create -f 'app-fusiion\Deployments-fusiion\client-deployment-fusiion.yaml'
$ kubectl create -f 'app-fusiion\Deployments-fusiion\collaborateurs-deployment-fusiion.yaml'
$ kubectl create -f 'app-fusiion\Deployments-fusiion\competences-deployment-fusiion.yaml'
$ kubectl create -f 'app-fusiion\Deployments-fusiion\front-deployment-fusiion.yaml'
$ kubectl create -f 'app-fusiion\Deployments-fusiion\gaming-deployment-fusiion.yaml'
$ kubectl create -f 'app-fusiion\Deployments-fusiion\notification-deployment-fusiion.yaml'
$ kubectl create -f 'app-fusiion\Deployments-fusiion\statistiques-deployment-fusiion.yaml'
```
OR 
```sh
$ kubectl create -f 'app-fusiion\Deployments-fusiion\'
```