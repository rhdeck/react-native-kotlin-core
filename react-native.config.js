const {
  existsSync,
  readFileSync,
  writeFileSync,
  copyFileSync,
  unlinkSync,
  renameSync,
} = require("fs");
const promote = require("promote-peer-dependencies");
const { join } = require("path");
const { sync: glob } = require("glob");
module.exports = {
  commands: [
    {
      name: "kotlin-link",
      description: "Initialize react-native-kotlin configuration and packages",
      func: () => {
        promote(process.cwd());
        //replace MainApplication.java
        const maPaths = glob(
          join(
            process.cwd(),
            "android",
            "app",
            "src",
            "main",
            "**",
            "MainApplication.java"
          )
        );
        const maPath = maPaths[0];
        const txt = readFileSync(maPath, { encoding: "utf8" });
        if (!txt.includes("RNKPackage")) {
          const lines = txt.split("\n");
          const packageLine = lines.find((line) => line.includes("package "));
          const templatePath = join(
            __dirname,
            "templates",
            "MainApplication.java"
          );
          const maTemplate = readFileSync(templatePath, { encoding: "utf8" });
          writeFileSync(maPath, [packageLine, maTemplate].join("\n"));
        }

        const macPaths = glob(
          join(
            process.cwd(),
            "android",
            "app",
            "src",
            "main",
            "**",
            "MainActivity.java"
          )
        );
        const macPath = macPaths[0];
        let macText = readFileSync(macPath, { encoding: "utf8" });
        const oldText = "import com.facebook.react.ReactActivity;";
        const newText = "import com.rnk.core.RNKActivity;";
        if (!macText.includes(newText)) {
          if (macText.includes(oldText)) {
            macText = macText.replace(oldText, newText);
          }
          const olddef = "public class MainActivity extends ReactActivity";
          const newdef = "public class MainActivity extends RNKActivity";
          writeFileSync(macPath, macText.replace(olddef, newdef));
        }
        //walk dependencies
        const { dependencies = {}, devDependencies = {} } = JSON.parse(
          readFileSync(join(process.cwd(), "package.json"))
        );
        const deps = Object.keys({ ...dependencies, ...devDependencies });
        //find hooks
        deps.forEach((dep) => {
          //find the package.json
          let packagePath;
          let path = process.cwd();
          while (!packagePath) {
            if (path === "/" || !existsSync(path))
              throw "Couldnot find dependency " + dep;
            const temp = join(path, "node_modules", dep);
            if (existsSync(temp)) {
              packagePath = temp;
            } else {
              path = join(path, "..");
            }
          }
          const kpath = join(packagePath, "react-native-kotlin.config.js");
          if (existsSync(kpath)) {
            const { prelink } = require(kpath);
            if (prelink) {
              prelink();
            }
          }
        });
      },
    },
  ],
};
