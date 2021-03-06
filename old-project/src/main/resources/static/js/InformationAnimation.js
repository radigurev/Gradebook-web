const multiStepForm=document.querySelector("[data-multi-step]");
const formSteps= [...multiStepForm.querySelectorAll("[data-step]")];


let currentStep = formSteps.findIndex(step => {
    return step.classList.contains("active")
})
if(currentStep < 0)
currentStep = 0
formSteps[currentStep].classList.add("active") 

multiStepForm.addEventListener("click",e=>{
    let incrementor
    if(e.target.matches("[data-next]")) {
        incrementor=1
    }else if (e.target.matches("[data-previous]")){
        incrementor = -1
    }else {
        return
    }
    if(incrementor==null) return
    const inputs = [...formSteps[currentStep].querySelectorAll("input")]
     const allValid = inputs.every(input => input.reportValidity())
         if(allValid){
             currentStep+=incrementor
         showCurrentStep()
         }
})

formSteps.forEach(step=>{
    step.addEventListener("animationend",(e)=>{
        formSteps[currentStep].classList.remove("hide")
       e.target.classList.toggle("hide",!e.target.classList.contains("active"))
    })
})

function showCurrentStep() {
    formSteps.forEach((step,index)=>{
        step.classList
        .toggle("active",index === currentStep) 
    
    })

}

var citySelect=document.getElementById("city")
var sel =document.getElementById("country")
    sel.addEventListener("mouseout",changeVisibility)

function changeVisibility(){
    var text = sel.options[sel.selectedIndex].text;
    if (text!=="Select Country"){
        console.log(text)
        citySelect.classList.remove("hide")
    }
    if(text==="Select Country"){
        console.log("remove city option")
        citySelect.classList.add("hide")
    }
}
