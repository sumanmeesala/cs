package hudson.cli;

import hudson.model.*;
import jenkins.model.Jenkins;
import hudson.model.ListView;
import jenkins.model.GlobalConfiguration;

def environment = System.getenv('env') ?: "default"

def jenkins = Jenkins.instance
def viewName = '${environment}_csJobs'
def existingView = jenkins.getView(viewName)

if (existingView == null) {
   def newView = new ListView(viewName)
   jenkins.addView(newView)
   println("View '$viewName' created.")
   } else {
   println("View '$viewName' already exists.")
}

matrixJob('${environment}_cs1stjob') {
    description('This is an 1st cc Job DSL job')

    configure { project ->
        project / 'logRotator' {
            numToKeep(4)
                               }
              }
              
    concurrentBuild(true)
    
    scm {
    none()
    }
    
    parameters {
      stringParam('custName','NONE','desc')
      }
      

       
    axes {
    axis {
       name('MY_AXIS_NAME') // Specify the axis name
        values('user') // Define the values for the user-defined axis
          }
        }

def globalConfiguration = GlobalConfiguration.all().find { it.displayName == 'Mask Passwords and Regexes' }

if (globalConfiguration) {
           globalConfiguration.setMaskPasswords(true)
           globalConfiguration.setMaskPasswordsConsoleLog(true)
           globalConfiguration.save()
           
           System.setProperty("org.jenkinsci.plugins.workflow.steps.SecretEnvVarHelper.CONFIGURED", "true")
           println("Enabled passwords")
           } else {
            println("Enabled passwordsnot found")
                   }
                   
    def pName = "${custName}"

    steps {
        shell('echo ${environment} "Im Athna_cc_trigger" ${pName}')
    }

                   
myView = hudson.model.Hudson.instance.getView('${environment}_csJobs')
myView.doAddJobToView('${environment}_cs1stjob') 
jenkins.save()
}

