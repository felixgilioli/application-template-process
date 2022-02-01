document.querySelector("#download-project-btn").onclick = function(event) {
    event.preventDefault();

    var propertiesInput = document.querySelectorAll("#app .property");

    var projectName = document.querySelector("#projectArtifactName").value;

    var repositoryUrl = getQueryParams(document.location.search)["repositoryUrl"];
//    repositoryUrl = "https://github.com/felixgilioli/template01-example.git";
    var dto = {
        "projectName": projectName,
        "repositoryUrl": repositoryUrl,
        "properties": {}
    };

    for (var i = 0; i < propertiesInput.length; i++) {
        if (propertiesInput[i].type === "checkbox") {
            dto["properties"][propertiesInput[i].name] = propertiesInput[i].checked;
        } else {
            dto["properties"][propertiesInput[i].name] = propertiesInput[i].value;
        }
    }

     fetch("http://localhost:8080/project/new", { method: "POST", body: JSON.stringify(dto), headers: {"Content-Type": "application/json; charset=utf-8"} })
      .then( res => {
        if (res.status !== 200) {
            alert("error");
        } else {
            res.blob().then( blob => {
                var url = window.URL.createObjectURL(blob);
                var a = document.createElement('a');
                a.href = url;
                a.download = projectName + ".zip";
                document.body.appendChild(a);
                a.click();
                a.remove();
            });
        }
      })
      .catch(error => {
        console.log('request failed', error);
      });

    console.log(dto);

}


function getQueryParams(qs) {
    qs = qs.split('+').join(' ');

    var params = {},
        tokens,
        re = /[?&]?([^=]+)=([^&]*)/g;

    while (tokens = re.exec(qs)) {
        params[decodeURIComponent(tokens[1])] = decodeURIComponent(tokens[2]);
    }

    return params;
}