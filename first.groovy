import hudson.model.*
import hudson.matrix.*
import hudson.util.*
import jenkins.model.Jenkins

def environment = env  
def custName = 'NONE'
def viewName = "${environment}_csJobs"
def jobName = "${environment}_cs1stjob"

// Create or retrieve the view
def jenkins = Jenkins.instance
def existingView = jenkins.getView(viewName)

if (existingView == null) {
   def newView = new ListView(viewName)
   jenkins.addView(newView)
   println("View '$viewName' created.")
} else {
   println("View '$viewName' already exists.")
}

// Define the matrix job
def matrixJob = jenkins.createProject(MatrixProject, jobName)
matrixJob.setDescription("This is a Matrix Job DSL job")

// Define the axes
def axes = []
axes.add(new Axis("DYNAMIC_AXIS", ["valueA", "valueB", "valueC"]))

// Configure the axes for the matrix job
matrixJob.setAxes(new AxisList(axes))

// Add the string parameter
matrixJob.addProperty(new ParametersDefinitionProperty(
    new StringParameterDefinition('custName', custName, 'Description')
))

// Configure concurrent builds
matrixJob.setConcurrentBuild(true)

// Configure SCM (none)
matrixJob.setScm(new NullSCM())

// Configure password masking
def globalConfiguration = GlobalConfiguration.all().find { it.displayName == 'Mask Passwords and Regexes' }

if (globalConfiguration) {
    globalConfiguration.setMaskPasswords(true)
    globalConfiguration.setMaskPasswordsConsoleLog(true)
    globalConfiguration.save()

    System.setProperty("org.jenkinsci.plugins.workflow.steps.SecretEnvVarHelper.CONFIGURED", "true")
    println("Enabled passwords")
} else {
    println("Enabled passwords not found")
}


// Save the job
matrixJob.save()

// Add the job to the view
existingView.add(matrixJob)
existingView.save()

println("Matrix job '$jobName' added to view '$viewName'")

